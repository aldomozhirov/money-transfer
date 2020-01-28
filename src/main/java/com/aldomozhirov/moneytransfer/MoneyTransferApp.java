package com.aldomozhirov.moneytransfer;

import com.aldomozhirov.moneytransfer.controller.AccountController;
import com.aldomozhirov.moneytransfer.controller.TransactionController;
import com.aldomozhirov.moneytransfer.controller.UserController;
import com.aldomozhirov.moneytransfer.mapper.CheckedExceptionMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyTransferApp {

    private static RepositoryFactory repositoryFactory;

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start() throws Exception {

        List<String> controllers = new ArrayList<>();
        controllers.add(AccountController.class.getCanonicalName());
        controllers.add(TransactionController.class.getCanonicalName());
        controllers.add(UserController.class.getCanonicalName());
        controllers.add(CheckedExceptionMapper.class.getCanonicalName());

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitParameter(
                "jersey.config.server.provider.classnames",
                controllers.stream().map(Object::toString).collect(Collectors.joining(","))
        );
        server.start();

        repositoryFactory = RepositoryFactory.getInstance();

    }

    public static RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

}
