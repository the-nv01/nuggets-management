package nuggets.demo.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionDetails implements Serializable {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(SessionDetails.class);
    private static final long serialVersionUID = -1729152110163638939L;
    private Timestamp sessionVaildTime;
    private Map<String, String> form;

    public SessionDetails() {
    }

    protected String getConcatName(String userFName, String userLName) {
        String fName = userFName == null ? "" : userFName;
        String lName = userLName == null ? "" : userLName;
        if (!fName.equals("") && !lName.equals("")) {
            return fName + " " + lName;
        } else {
            return !fName.equals("") ? fName : lName;
        }
    }

    public <T> T getForm(String key, Class<T> type) {
        String jsonStr = (String)this.form.get(key);
        return this.convertObject(jsonStr, type);
    }

    public void setForm(String key, Object form) {
        String jsonStr = this.convertJsonString(form);
        if (this.form != null) {
            this.form.put(key, jsonStr);
        } else {
            Map<String, String> map = new HashMap();
            map.put(key, jsonStr);
            this.form = map;
        }

    }

    public boolean existsForm(String formName) {
        return this.getForm() != null && this.getForm().containsKey(formName);
    }

    private <T> T convertObject(String jsonStr, Class<T> type) {
        T form = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
            mapper.setTimeZone(TimeZone.getDefault());
            form = mapper.readValue(jsonStr, type);
        } catch (Exception var5) {
            log.warn("フォーム情報の復元に失敗しました。");
        }

        return form;
    }

    private String convertJsonString(Object form) {
        String jsonStr = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
            mapper.setTimeZone(TimeZone.getDefault());
            jsonStr = mapper.writeValueAsString(form);
        } catch (Exception var4) {
            log.warn("フォーム情報の変換に失敗しました。");
        }

        return jsonStr;
    }

    @Generated
    public void setSessionVaildTime(final Timestamp sessionVaildTime) {
        this.sessionVaildTime = sessionVaildTime;
    }

    @Generated
    public void setForm(final Map<String, String> form) {
        this.form = form;
    }

    @Generated
    public Timestamp getSessionVaildTime() {
        return this.sessionVaildTime;
    }

    @Generated
    public Map<String, String> getForm() {
        return this.form;
    }
}

