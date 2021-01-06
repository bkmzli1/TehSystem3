package ru.tehsystem.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tehsystem.demo.domain.Img;
import ru.tehsystem.demo.domain.Massages;
import ru.tehsystem.demo.domain.Task;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.domain.enums.Role;
import ru.tehsystem.demo.model.TaskCreate;
import ru.tehsystem.demo.repo.TaskRepo;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.TaskService;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/create")
    @ResponseBody
    public Task task(@RequestBody TaskCreate taskCreate, Authentication authentication) {
        return taskService.taskCrate(taskCreate, (User) authentication.getPrincipal());
    }

    @GetMapping("/tasks")
    @ResponseBody
    public Set<Task> tasks(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User userAllEx = userRepo.findOneByUsername("Все исполнители");

        AtomicBoolean root = new AtomicBoolean(false);
        user.getAuthorities().stream().filter(roles -> roles.getAuthority().equals(Role.ADMIN.getRole())).forEach(roles -> root.set(true));
        if (root.get()) {
            Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate));
            tasks.addAll(taskRepo.findAll());
            return tasks;
        } else {
            List<Task> taskList = new ArrayList<>(taskRepo.findByCreator(user));
            taskList.addAll(taskRepo.findByExecutor(user));
            AtomicBoolean Ex = new AtomicBoolean(false);
            user.getAuthorities().stream().filter(roles -> roles.getAuthority().equals(Role.EXECUTOR.getRole())).forEach(roles -> Ex.set(true));
            if (Ex.get())
                taskList.addAll(taskRepo.findByExecutor(userAllEx));
            Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate));
            tasks.addAll(taskList);
            return tasks;
        }
    }

    @GetMapping("/tasks/my")
    @ResponseBody
    public Set<Task> tasksMy(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Task> taskList = new ArrayList<>(taskRepo.findByCreator(user));
        Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getCrate));
        tasks.addAll(taskList);
        return tasks;

    }

    @GetMapping("/get/task/{id}")
    @ResponseBody
    public Task task(@PathVariable String id) {
        Task task = taskRepo.findById(id).get();
        Set<Massages> projects = new TreeSet<>(Comparator.comparing(Massages::getDateTime));
        projects.addAll(task.getMassages());
        task.setMassages(projects);
        return task;
    }

    @GetMapping("/bin/{id}")
    @ResponseBody
    public Task taskBin(Authentication authentication, @PathVariable String id) {
        User user = (User) authentication.getPrincipal();
        Task task = taskRepo.findById(id).get();
        return this.taskService.taskFin(task, user);
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
        response.setHeader(
                "Content-Disposition", "attachment; filename=" + taskRepo.findById(id).get().getName() + ".zip");
        response.setHeader("Content-Transfer-Encoding", "binary");
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
