package com.gdupt.shiro;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

/**
 * @author xuhuaping
 * @date 2022/11/16
 */
public class JWTDefaultSubjectFactory extends DefaultSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建shiro内部的session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
