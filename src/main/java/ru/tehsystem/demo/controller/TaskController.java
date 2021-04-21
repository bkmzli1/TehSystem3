package ru.tehsystem.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ibm.icu.text.Transliterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tehsystem.demo.domain.*;
import ru.tehsystem.demo.model.TaskCreate;
import ru.tehsystem.demo.repo.TaskRepo;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public TaskController(TaskService taskService, TaskRepo taskRepo, UserRepo userRepo) {
        this.taskService = taskService;
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }
    @JsonView(Views.TaskAll.class)
    @PostMapping("/create")
    @ResponseBody
    public Object task(@RequestBody() @Valid TaskCreate taskCreate, BindingResult bindingResult,
                       HttpServletRequest request, Authentication authentication) {
        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            try {
                return taskService.taskCrate(taskCreate, (User) authentication.getPrincipal());
            } catch (NullPointerException npe) {
                return taskService.taskCrate(taskCreate, userRepo.findAll().get(0));
            }
        }

    }
    @JsonView(Views.TaskAll.class)
    @PostMapping("/bin/ex/{id}")
    @ResponseBody
    public boolean ex(Authentication authentication, @PathVariable String id) {
        User user = (User) authentication.getPrincipal();
        Task task = taskRepo.findById(id).get();
        if (user.equals(task.getExecutor())) {
            return true;
        }
        AtomicBoolean b = new AtomicBoolean(false);
        user.getAuthorities().forEach(roles -> b.set(roles.getAuthority().equals("ADMIN")));
        return b.get();
    }
    @JsonView(Views.TaskAll.class)
    @GetMapping("/tasks")
    @ResponseBody

    public Set<Task> tasks(Authentication authentication) {
        User user;
        try {
            user = (User) authentication.getPrincipal();
        } catch (NullPointerException npe) {
            user = userRepo.findAll().get(0);
        }

        User userAllEx = userRepo.findOneByUsername("Все исполнители");

        AtomicBoolean root = new AtomicBoolean(false);
        user.getAuthorities().stream().filter(roles -> roles.getAuthority().equals("ADMIN"))
            .forEach(roles -> root.set(true));
        if (root.get()) {
            Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate).reversed());
            tasks.addAll(taskRepo.findAll());

            tasks.removeIf(Task::isDoneCrate);
            tasks.removeIf(Task::isDeletes);
            return tasks;
        } else {
            List<Task> taskList = new ArrayList<>(taskRepo.findByCreator(user));
            taskList.addAll(taskRepo.findByExecutor(user));
            AtomicBoolean Ex = new AtomicBoolean(false);
            user.getAuthorities().stream().filter(roles -> roles.getAuthority().equals("EXECUTOR"))
                .forEach(roles -> Ex.set(true));
            if (Ex.get())
                taskList.addAll(taskRepo.findByExecutor(userAllEx));
            Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate).reversed());
            tasks.addAll(taskList);
            tasks.removeIf(task -> task.isDoneCrate());

            return tasks;
        }
    }
    @JsonView(Views.TaskAll.class)
    @PostMapping("/tasks")
    @ResponseBody
    public Set<Task> tasksUpdate(Authentication authentication) throws InterruptedException {
        Thread.sleep(5000);
        return tasks(authentication);
    }
    @JsonView(Views.TaskBasic.class)
    @GetMapping("/tasking")
    @ResponseBody
    public Set<Task> tasking(Authentication authentication) {
        User user;
        try {
            user = (User) authentication.getPrincipal();
        } catch (NullPointerException npe) {
            user = userRepo.findAll().get(0);
        }

        User userAllEx = userRepo.findOneByUsername("Все исполнители");

        AtomicBoolean root = new AtomicBoolean(false);
        user.getAuthorities().stream().filter(roles -> roles.getAuthority().equals("ADMIN"))
            .forEach(roles -> root.set(true));
        if (root.get()) {
            Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate));
            tasks.addAll(taskRepo.findAll());

            tasks.removeIf(task -> !task.isDoneCrate());
            return tasks;
        } else {
            List<Task> taskList = new ArrayList<>(taskRepo.findByCreator(user));
            taskList.addAll(taskRepo.findByExecutor(user));
            AtomicBoolean Ex = new AtomicBoolean(false);
            user.getAuthorities().stream().filter(roles -> roles.getAuthority().equals("EXECUTOR"))
                .forEach(roles -> Ex.set(true));
            if (Ex.get())
                taskList.addAll(taskRepo.findByExecutor(userAllEx));
            Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate));
            tasks.addAll(taskList);
            tasks.removeIf(task -> !task.isDoneCrate());
            tasks.removeIf(Task::isDeletes);

            return tasks;
        }
    }
    @JsonView(Views.TaskBasic.class)
    @GetMapping("/tasks/my")
    @ResponseBody
    public Set<Task> tasksMy(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Task> taskList = new ArrayList<>(taskRepo.findByCreator(user));
        Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate));
        tasks.addAll(taskList);
        return tasks;

    }

    @JsonView(Views.TaskAll.class)
    @GetMapping("/get/{id}")
    @ResponseBody
    public Task task(@PathVariable String id) {
        Task task = taskRepo.findById(id).get();
        Set<Massages> projects = new TreeSet<>(Comparator.comparing(Massages::getDateTime));
        projects.addAll(task.getMassages());
        task.setMassages(projects);
        return task;
    }
    @JsonView(Views.TaskAll.class)
    @PostMapping("/get/{id}")
    @ResponseBody
    public Task taskUpload(@PathVariable String id) throws InterruptedException {
        System.out.println("sleep");
        Thread.sleep(5000);
        Task task = taskRepo.findById(id).get();
        Set<Massages> projects = new TreeSet<>(Comparator.comparing(Massages::getDateTime));
        projects.addAll(task.getMassages());
        task.setMassages(projects);
        System.out.println("sleepOut");
        return task;
    }
    @JsonView(Views.TaskAll.class)
    @GetMapping("/bin/{id}")
    @ResponseBody
    public Task taskBin(Authentication authentication, @PathVariable String id) {
        User user = (User) authentication.getPrincipal();
        Task task = taskRepo.findById(id).get();
        return this.taskService.taskFin(task, user);
    }
    @JsonView(Views.TaskAll.class)
    @PostMapping("/binCrate/{id}")
    @ResponseBody
    public Task taskBinCrate(Authentication authentication, @PathVariable String id, @RequestBody boolean fin) {
        User user = (User) authentication.getPrincipal();
        Task task = taskRepo.findById(id).get();
        return this.taskService.taskFinCrate(task, user, fin);
    }
    @JsonView(Views.TaskAll.class)
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Task taskDelete(Authentication authentication, @PathVariable String id) {
        //        User user = (User) authentication.getPrincipal();
        Task task = taskRepo.findById(id).get();
        return this.taskService.taskDelete(task, new User());
    }


    @GetMapping("/download/{id}")
    public void concatenationFiles(@PathVariable(value = "id") String id, HttpServletResponse response) {

        Set<Img> downloadList = new TreeSet<>(Comparator.comparing(Img::getName));
        downloadList.addAll(taskRepo.findById(id).get().getImgs());
        String archiveName = uploadPath + "temp/" + id + ".zip";
        File a = new File(uploadPath + "temp/");
        a.mkdirs();
        doZip(downloadList, archiveName);
        response.setContentType(archiveName);
    ;
        Transliterator toLatinTrans = Transliterator.getInstance("Cyrillic-Latin");
        String result = toLatinTrans.transliterate((taskRepo.findById(id).get().getName() + ".zip").replace(" ", "_"));
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.setHeader(
                "Content-Disposition", "attachment; filename=" + result);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(archiveName);
            int len;
            byte[] buf = new byte[fis.available()];
            while ((len = fis.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            bos.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }


    private void doZip(Set<Img> files, String archiveName) {

        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(archiveName));
            out.setLevel(9);
            for (Img img : files) {
                File file = new File(uploadPath + "/" + img.getImg().replace("static/", ""));
                out.putNextEntry(new ZipEntry(file.getName()));
                write(new FileInputStream(file), out);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
