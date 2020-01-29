package com.aldomozhirov.moneytransfer;

import com.aldomozhirov.moneytransfer.controller.AccountController;
import com.aldomozhirov.moneytransfer.controller.TransactionController;
import com.aldomozhirov.moneytransfer.controller.UserController;
import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import com.aldomozhirov.moneytransfer.mapper.CheckedExceptionMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyTransferApp {

    public static void main(String[] args) {
        try {
            start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(int port) throws Exception {

        // Initialize controllers list to handle

        List<String> controllers = new ArrayList<>();
        controllers.add(AccountController.class.getCanonicalName());
        controllers.add(TransactionController.class.getCanonicalName());
        controllers.add(UserController.class.getCanonicalName());
        controllers.add(CheckedExceptionMapper.class.getCanonicalName());

        // Initialize repositories

        RepositoryFactory.create();

        // Initialize and start server

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitParameter(
                "jersey.config.server.provider.classnames",
                controllers.stream().map(Object::toString).collect(Collectors.joining(","))
        );
        server.start();

    }

    public static RepositoryFactory getRepositoryFactory() {
        return RepositoryFactory.getInstance();
    }

}
