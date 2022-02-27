package com.exam.core.realm;

import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TeacherDO;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 鉴权器
 * @author lth
 */


public class CustomerAutheizer extends ModularRealmAuthorizer {

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Object primaryPrincipal = principals.getPrimaryPrincipal();

        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            if (primaryPrincipal instanceof TeacherDO) {
                if (realm instanceof ExamRealm) {
                    return ((ExamRealm) realm).isPermitted(principals, permission);
                }
            }
            if (primaryPrincipal instanceof StudentDO) {
                if (realm instanceof StudentRealm) {
                    return ((StudentRealm) realm).isPermitted(principals, permission);
                }
            }

        }
        return false;
    }

}
