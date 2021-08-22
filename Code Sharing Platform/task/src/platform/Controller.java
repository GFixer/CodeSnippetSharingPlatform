package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    SharedCodeService sharedCodeService;

    @GetMapping("/api/code/{uuid}")
    @ResponseBody
    public Object getCodeJson(@PathVariable String uuid, HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        SharedCode sharedCode = sharedCodeService.findSharedCodeById(uuid);
        if (sharedCode == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (checkIfAllowedToView(sharedCode)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return sharedCode;
    }

    private boolean checkIfAllowedToView(SharedCode sharedCode) {
        checkRestrictions(sharedCode);
        if (sharedCode.isViewsResctricted() && sharedCode.getViews() < 0){
            return true;
        }
        if (sharedCode.isTimeRestricted() && sharedCode.getTime() < 0){
            return true;
        }
        return false;
    }

    private void checkRestrictions(SharedCode sharedCode) {
        if (sharedCode.isTimeRestricted()) {
            Duration duration = Duration.between(
                    LocalDateTime.now(), sharedCode.getDate().truncatedTo(ChronoUnit.SECONDS));
            long diff = Math.abs(duration.toSeconds());
            sharedCode.setTime(sharedCode.getTime() - diff);
        }
        if (sharedCode.isViewsResctricted()) {
            sharedCode.setViews(sharedCode.getViews() - 1);
        }
        sharedCodeService.save(sharedCode);
    }

    @GetMapping("/code/{uuid}")
    public Object getCode(@PathVariable String uuid, HttpServletResponse response, Model model) {
        response.addHeader("Content-Type", "text/html");
        SharedCode sharedCode = sharedCodeService.findSharedCodeById(uuid);
        if (checkIfAllowedToView(sharedCode)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        model.addAttribute("code", sharedCode);
        return "code";
    }

    @GetMapping("/code/new")
    public String postCodeHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        return "create";
    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    public Object[] getLatestCodeJson(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        List<SharedCode> top10List = sharedCodeService.findTop10ByOrderByDateDesc();
        return top10List.toArray();
    }


    @GetMapping("/code/latest")
    public String getLatestHtml(HttpServletResponse response, Model model) {
        response.addHeader("Content-Type", "text/html");
        List<SharedCode> top10List = sharedCodeService.findTop10ByOrderByDateDesc();
        model.addAttribute("codeList", top10List);
        return "latest";
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public Object postCode(@RequestBody SharedCodeReceived uploadedCode) {
        SharedCode code = new SharedCode(uploadedCode.getCode(),
                uploadedCode.getTime(), uploadedCode.getViews());
        if (uploadedCode.getTime() > 0) {
            code.setTimeRestricted(true);
        }
        if (uploadedCode.getViews() > 0) {
            code.setViewsResctricted(true);
        }
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        code.setId(randomUUIDString);
        code.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        SharedCode codeSaved = sharedCodeService.save(code);
        return Collections.singletonMap("id", String.valueOf(codeSaved.getId()));
    }
}
