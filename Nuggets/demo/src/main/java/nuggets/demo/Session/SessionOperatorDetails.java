package nuggets.demo.Session;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionOperatorDetails extends SessionDetails implements Serializable {
    private static final long serialVersionUID = 5181208188270239512L;
    private String userId;
    private String username;
    private String loginId;
    private String operatorId;
    private String operatorFName;
    private String operatorLName;
    private List<String> authorityIds;
    private List<String> authorityItems;

    public SessionOperatorDetails() {
    }

    public String getUserId() {
        return this.operatorId;
    }

    public String getConcatName() {
        return super.getConcatName(this.operatorFName, this.operatorLName);
    }

    public boolean hasSpecial() {
        return this.authorityIds.contains("special01");
    }

    public boolean hasSpecialReserve() {
        return this.authorityIds.contains("special02");
    }

    public boolean hasAuthority(String authority) {
        return this.authorityIds.contains(authority);
    }

    @Generated
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    @Generated
    public void setUsername(final String username) {
        this.username = username;
    }

    @Generated
    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

    @Generated
    public void setOperatorId(final String operatorId) {
        this.operatorId = operatorId;
    }

    @Generated
    public void setOperatorFName(final String operatorFName) {
        this.operatorFName = operatorFName;
    }

    @Generated
    public void setOperatorLName(final String operatorLName) {
        this.operatorLName = operatorLName;
    }

    @Generated
    public void setAuthorityIds(final List<String> authorityIds) {
        this.authorityIds = authorityIds;
    }

    @Generated
    public void setAuthorityItems(final List<String> authorityItems) {
        this.authorityItems = authorityItems;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getLoginId() {
        return this.loginId;
    }

    @Generated
    public String getOperatorId() {
        return this.operatorId;
    }

    @Generated
    public String getOperatorFName() {
        return this.operatorFName;
    }

    @Generated
    public String getOperatorLName() {
        return this.operatorLName;
    }

    @Generated
    public List<String> getAuthorityIds() {
        return this.authorityIds;
    }

    @Generated
    public List<String> getAuthorityItems() {
        return this.authorityItems;
    }
}
