package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SharedCode {

    @Id
    @Column
    private String id;
    @Column
    private String code;
    @Column
    private LocalDateTime date = LocalDateTime.now();
    @Column
    private Long time;
    @Column
    private Long views;
    private boolean viewsResctricted;
    private boolean timeRestricted;

    public SharedCode(String code, Long time, Long views) {
        this.code = code;
        this.time = time;
        this.views = views;
    }

    public SharedCode() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @JsonIgnore
    public boolean isViewsResctricted() {
        return viewsResctricted;
    }

    public void setViewsResctricted(boolean viewsResctricted) {
        this.viewsResctricted = viewsResctricted;
    }

    @JsonIgnore
    public boolean isTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }


    public String toHtml() {
        return "<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<span id=\"load_date\">" +
                date + "</span>" +
                "    <pre id=\"code_snippet\">\n" +
                code +
                "</pre>\n" +
                "</body>\n" +
                "</html>";
    }
}
