package ru.tehsystem.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import ru.tehsystem.demo.domain.enums.Level;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private Level level;
    private String name;
    private String text;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Img> imgs;
    @ManyToOne
    private User creator;
    @ManyToMany(fetch = FetchType.EAGER )
    private Set<User> executor;
    @ManyToOne
    private User performed;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Massages> massages;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime crate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime executed;

    private boolean done =false;
    private boolean doneCrate =false;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Img> getImgs() {
        return imgs;
    }

    public void setImgs(Set<Img> imgs) {
        this.imgs = imgs;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<User> getExecutor() {
        return executor;
    }

    public void setExecutor(Set<User> executor) {
        this.executor = executor;
    }

    public Set<Massages> getMassages() {
        return massages;
    }

    public void setMassages(Set<Massages> massages) {
        this.massages = massages;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCrate() {
        return crate;
    }

    public void setCrate(LocalDateTime crate) {
        this.crate = crate;
    }

    public LocalDateTime getExecuted() {
        return executed;
    }

    public void setExecuted(LocalDateTime executed) {
        this.executed = executed;
    }

    public User getPerformed() {
        return performed;
    }

    public void setPerformed(User performed) {
        this.performed = performed;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDoneCrate() {
        return doneCrate;
    }

    public void setDoneCrate(boolean doneCrate) {
        this.doneCrate = doneCrate;
    }
}
