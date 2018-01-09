package com.sample.minhnd.tranningapplication2;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by W10-PRO on 4/1/2018.
 */

public class DataModel {
    private Realm realm = Realm.getDefaultInstance();

    public RealmResults<User> getAllUser(){
        RealmQuery<User> query = realm.where(User.class);
        return query.findAll();
    }

    public User findUserByAccount(String account){
        RealmQuery<User> query = realm.where(User.class).equalTo("account", account);
        return query.findFirst();
    }

    public void editAvatar(String file, String account){
        User update = realm.where(User.class).equalTo("account", account).findFirst();
        realm.beginTransaction();
        update.setAvatarUri(file);
        realm.copyToRealmOrUpdate(update);
        realm.commitTransaction();
    }

    public void editName(String name, String account){
        User update = realm.where(User.class).equalTo("account", account).findFirst();
        realm.beginTransaction();
        update.setFullName(name);
        realm.copyToRealmOrUpdate(update);
        realm.commitTransaction();
    }

    public void addNewUser(User user){
        realm.beginTransaction();
        realm.insertOrUpdate(user);
        realm.commitTransaction();
    }

    public void deleteUser(final User user) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> result = realm.where(User.class).equalTo("account", user.getAccount()).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

}
