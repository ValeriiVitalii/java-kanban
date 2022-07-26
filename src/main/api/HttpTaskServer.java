package api;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import taskManagers.Manager;
import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static Gson gson = new Gson();
    protected static HttpTaskManager httpTaskManager =
            Manager.getDefaultHttpTaskManager("http://localhost:8078", false);
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
    }

    public static HttpTaskManager getHttpTaskManager() {
        return httpTaskManager;
    }
    static class TasksHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            String response = null;
            int rCode;
            switch (method) {
                case "GET":
                    response = GETRequest(path, exchange);
                    rCode = 200;
                    break;
                case "POST":
                    POSTRequest(path, exchange);
                    rCode = 201;
                    break;
                case "DELETE":
                    response = DELETERequest(path, exchange);
                    rCode = 200;
                    break;
                default:
                    response = gson.toJson("bad method type. use GET, POST or DELETE please");
                    rCode = 400;
            }

            exchange.sendResponseHeaders(rCode, 0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private static String GETRequest(String path, HttpExchange exchange) {
        String response = null;
        if (Pattern.matches("^/tasks$", path)) {
            response = gson.toJson(httpTaskManager.getAllTasks().values());
        } else if (Pattern.matches("^/tasks/task$", path) && exchange.getRequestURI().getQuery() != null) {
            String param = exchange.getRequestURI().getQuery();
            String[] paramArray = param.split("=");
            int id = Integer.parseInt(paramArray[1]);
            response = gson.toJson(httpTaskManager.getTask(id));
        } else if (Pattern.matches("^/tasks/task$", path)) {
            response = gson.toJson(httpTaskManager.getMapTask().values());
        } else if (Pattern.matches("^/tasks/subtask/epic$", path) && exchange.getRequestURI().getQuery() != null) {
            String param = exchange.getRequestURI().getQuery();
            String[] paramArray = param.split("=");
            int id = Integer.parseInt(paramArray[1]);
            response = gson.toJson(httpTaskManager.getListSubtasksOfAnEpic(id));
        } else if (Pattern.matches("^/tasks/epic$", path) && exchange.getRequestURI().getQuery() != null) {
            String param = exchange.getRequestURI().getQuery();
            String[] paramArray = param.split("=");
            int id = Integer.parseInt(paramArray[1]);
            response = gson.toJson(httpTaskManager.getEpic(id));
        } else if (Pattern.matches("^/tasks/epic$", path)) {
            response = gson.toJson(httpTaskManager.getMapEpic().values());
        } else if (Pattern.matches("^/tasks/subtask$", path) && exchange.getRequestURI().getQuery() != null) {
            String param = exchange.getRequestURI().getQuery();
            String[] paramArray = param.split("=");
            int id = Integer.parseInt(paramArray[1]);
            response = gson.toJson(httpTaskManager.getSubtask(id));
        } else if (Pattern.matches("^/tasks/subtask$", path)) {
            response = gson.toJson(httpTaskManager.getMapSubtask().values());
        } else if (Pattern.matches("^/tasks/history$", path)) {
            response = gson.toJson(httpTaskManager.getHistory());
        } else if (Pattern.matches("^/tasks/priority$", path)) {
            response = gson.toJson(httpTaskManager.getPrioritizedTasks());
        }
        return response;
    }

    private static void POSTRequest(String path, HttpExchange exchange) throws IOException {
        if (Pattern.matches("^/tasks/task$", path)) {
            InputStream inputStream = exchange.getRequestBody();
            String taskBody = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Task task = gson.fromJson(taskBody, Task.class);
            if (httpTaskManager.getMapTask().containsKey(task.getId())) {
                httpTaskManager.udpate(task);
                httpTaskManager.getAllTasks().put(task.getId(), task);
            } else {
                httpTaskManager.add(task);
                httpTaskManager.getAllTasks().put(task.getId(), task);
            }
        } else if (Pattern.matches("^/tasks/epic$", path)) {
            InputStream inputStream = exchange.getRequestBody();
            String epicBody = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Epic epic = gson.fromJson(epicBody, Epic.class);
            if (httpTaskManager.getMapTask().containsKey(epic.getId())) {
                httpTaskManager.udpate(epic);
                httpTaskManager.getAllTasks().put(epic.getId(), epic);
            } else {
                httpTaskManager.add(epic);
                httpTaskManager.getAllTasks().put(epic.getId(), epic);
            }
        } else if (Pattern.matches("^/tasks/subtask$", path)) {
            InputStream inputStream = exchange.getRequestBody();
            String subtaskBody = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Subtask subtask = gson.fromJson(subtaskBody, Subtask.class);
            if (httpTaskManager.getMapTask().containsKey(subtask.getId())) {
                httpTaskManager.udpate(subtask);
                httpTaskManager.getAllTasks().put(subtask.getId(), subtask);
            } else {
                httpTaskManager.add(subtask);
                httpTaskManager.getAllTasks().put(subtask.getId(), subtask);
            }
        }
    }

    private static String DELETERequest(String path, HttpExchange exchange) {
        String response = null;
        if (Pattern.matches("^/tasks/task$", path) && exchange.getRequestURI().getQuery() != null) {
            String param = exchange.getRequestURI().getQuery();
            String[] paramArray = param.split("=");
            int id = Integer.parseInt(paramArray[1]);
            httpTaskManager.removeSpecificTask(id);
            response = gson.toJson("task id=" + id + " deleted");
        } else if (Pattern.matches("^/tasks/task$", path)) {
            httpTaskManager.removeListTask();
            response = gson.toJson("tasks deleted");
        } else if (Pattern.matches("^/tasks/epic$", path) && exchange.getRequestURI().getQuery() != null) {
            String param = exchange.getRequestURI().getQuery();
            String[] paramArray = param.split("=");
            int id = Integer.parseInt(paramArray[1]);
            httpTaskManager.removeSpecificEpic(id);
            response = gson.toJson("epic id=" + id + " deleted");
        } else if (Pattern.matches("^/tasks/subtask$", path) && exchange.getRequestURI().getQuery() != null) {
            String param = exchange.getRequestURI().getQuery();
            String[] paramArray = param.split("=");
            int id = Integer.parseInt(paramArray[1]);
            httpTaskManager.removeSpecificSubtask(id);
            response = gson.toJson("subtask id=" + id + " deleted");
            System.out.println(httpTaskManager.getPrioritizedTasks());
        } else {
            response = gson.toJson("wrong operation");
        }
        return response;
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        httpServer.start();
    }

    public void stop() {
        System.out.println("Сервер остановлен");
        httpServer.stop(0);
    }

}


