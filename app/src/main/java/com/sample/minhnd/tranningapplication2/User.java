package com.sample.minhnd.tranningapplication2;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by W10-PRO on 4/1/2018.
 */

public class User extends RealmObject {
    private String fullName;
    @PrimaryKey
    private String account;
    private String avatarUri;

    public User() {
    }

    public User(String fullName, String account, String avatarUri) {
        this.fullName = fullName;
        this.account = account;
        this.avatarUri = avatarUri;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }
}
