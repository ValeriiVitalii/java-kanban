package httpserver;

import api.HttpTaskManager;
import api.HttpTaskServer;
import api.KVServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManagers.Manager;
import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ApiTest {
    HttpTaskManager httpTaskManager = Manager.getDefaultHttpTaskManager("http://localhost:8078", false);
    KVServer kvServer;
    HttpTaskServer httpTaskServer = new HttpTaskServer();
    Gson gson = Manager.getGson();

    Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1",
            "2021-11-22T16:22:10", 44);
    Task taskTest2 = new Task(2,"ТаскТест №2", "ТаскПроверка 2",
            "2021-11-22T17:22:10", 42);
    Epic epicTest = new Epic(3,"ЭпикТест №1", "ЭпикПроверка 1",
            "2021-11-22T18:22:10", 22);
    Subtask subtaskTest = new Subtask(4,"СубтаскТест №1", "СубтаскПроверка 1", 3,
            "2021-11-22T19:22:10", 453);
    Subtask subtaskTest2 = new Subtask(5,"СубтаскТест №2", "СубтаскПроверка 2", 3,
            "2021-11-22T20:22:10", 16);
    Subtask subtaskTest3 = new Subtask(6,"СубтаскТест №3", "СубтаскПроверка 3", 3,
            "2021-11-22T21:22:10", 64);
    Epic epicTest2 = new Epic(7,"ЭпикТест №2", "ЭпикПроверка 2",
            "2021-11-22T22:22:10", 61);


    public ApiTest() throws IOException {
    }

    @BeforeEach
    void start() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer.start();
    }

    @AfterEach
    void stop() {
        kvServer.stop();
        httpTaskServer.stop();
    }

    @Test
    void sendAndLoadAllTasksAndHistoryKVServer() {
        httpTaskManager.getToken();

        httpTaskManager.add(taskTest);
        httpTaskManager.add(taskTest2);
        httpTaskManager.add(epicTest);
        httpTaskManager.add(subtaskTest);
        httpTaskManager.add(subtaskTest2);
        httpTaskManager.add(subtaskTest3);
        httpTaskManager.add(epicTest2);
        httpTaskManager.getTask(1);
        httpTaskManager.getSubtask(5);
        httpTaskManager.save();
        HttpTaskManager loadTM = Manager.getDefaultHttpTaskManager("http://localhost:8078", true);


        assertEquals(httpTaskManager.getMapTask(), loadTM.getMapTask());
        assertEquals(httpTaskManager.getMapEpic(), loadTM.getMapEpic());
        assertEquals(httpTaskManager.getMapSubtask(), loadTM.getMapSubtask());
        assertEquals(httpTaskManager.getHistory(), loadTM.getHistory());
        assertEquals(httpTaskManager.getPrioritizedTasks(), loadTM.getPrioritizedTasks());

    }

    @Test
    void sendAndLoadTasksKVServer() {
        httpTaskManager.getToken();
        httpTaskManager.add(epicTest);
        httpTaskManager.save();
        HttpTaskManager loadTM = Manager.getDefaultHttpTaskManager("http://localhost:8078", true);
        assertEquals(httpTaskManager.getMapEpic(), loadTM.getMapEpic());
    }

    @Test
    void sendAndLoadSubtasksKVServer() {
        httpTaskManager.getToken();
        httpTaskManager.add(subtaskTest);
        httpTaskManager.save();
        HttpTaskManager loadTM = Manager.getDefaultHttpTaskManager("http://localhost:8078", true);
        assertEquals(httpTaskManager.getMapSubtask(), loadTM.getMapSubtask());
    }

    @Test
    void getPrioritizedTasksListTest() {
        httpTaskManager.getToken();
        httpTaskManager.add(taskTest);
        httpTaskManager.add(taskTest2);
        httpTaskManager.add(epicTest);
        httpTaskManager.add(subtaskTest);
        httpTaskManager.add(subtaskTest2);
        httpTaskManager.add(subtaskTest3);
        httpTaskManager.save();
        HttpTaskManager loadTM = Manager.getDefaultHttpTaskManager("http://localhost:8078", true);
        assertEquals(httpTaskManager.getPrioritizedTasks(), loadTM.getPrioritizedTasks());
    }

    @Test
    void sendAndLoadEpicsKVServer() {
        httpTaskManager.getToken();
        httpTaskManager.add(taskTest);
        httpTaskManager.add(taskTest2);
        httpTaskManager.save();
        HttpTaskManager loadTM = Manager.getDefaultHttpTaskManager("http://localhost:8078", true);
        assertEquals(httpTaskManager.getMapTask(), loadTM.getMapTask());
    }

    @Test
    void sendTaskToHttpServerTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        Task task = HttpTaskServer.getHttpTaskManager().getMapTask().get(1);
        assertEquals(task, taskTest);
    }

    @Test
    void sendEpicToHttpServerTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTest)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        Epic epic = HttpTaskServer.getHttpTaskManager().getMapEpic().get(3);
        assertEquals(epic, epicTest);
    }

    @Test
    void sendSubtaskToHttpServerTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtaskTest)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        Subtask subtask = HttpTaskServer.getHttpTaskManager().getMapSubtask().get(4);
        assertEquals(subtask, subtaskTest);
    }

    @Test
    void getTaskByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        //----------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            String json = response.body();
            Task task = gson.fromJson(json, Task.class);
            assertEquals(taskTest, task);
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    @Test
    void getEpicByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        //----------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/epic?id=3");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            String json = response.body();
            Epic epic = gson.fromJson(json, Epic.class);
            assertEquals(epicTest, epic);
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    @Test
    void getSubtaskByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtaskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        //----------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/subtask?id=4");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            String json = response.body();
            Subtask subtask = gson.fromJson(json, Subtask.class);
            assertEquals(subtaskTest, subtask);
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }
    @Test
    void getAllTasksEpicsSubtasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create("http://localhost:8080/tasks/epic");
        request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create("http://localhost:8080/tasks/subtask");
        request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtaskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        httpTaskManager.add(taskTest);
        httpTaskManager.add(epicTest);


        //------------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            String json = response.body();
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            List<Task> tasksList = gson.fromJson(json, type);
            assertEquals(3, tasksList.size());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    @Test
    void getPriorityTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create("http://localhost:8080/tasks/task");
        request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest2)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        httpTaskManager.add(taskTest);
        httpTaskManager.add(taskTest2);

        //---------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/priority");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            Type type = new TypeToken<List<Task>>(){}.getType();

            List<Task> expectedPrioritizedList = new ArrayList<>(httpTaskManager.getPrioritizedTasks());
            List<Task> prioritizedList = expectedPrioritizedList;
            assertEquals(prioritizedList, expectedPrioritizedList);
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }
    @Test
    void deleteTaskByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        //--------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/task?id=1");
        request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertNull(HttpTaskServer.getHttpTaskManager().getMapTask().get(1));
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    @Test
    void deleteEpicAndSubtasksByEpicIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtaskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create("http://localhost:8080/tasks/epic");
        request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epicTest2)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        //--------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/epic?id=7");
        request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertNull(HttpTaskServer.getHttpTaskManager().getMapEpic().get(7));
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    @Test
    void deleteSubtaskByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtaskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        //-----------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/subtask?id=4");
        request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertNull(HttpTaskServer.getHttpTaskManager().getSubtask(4));
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    @Test
    void deleteAllTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create("http://localhost:8080/tasks/task");
        request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(taskTest2)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        //-----------------------------------------------------------------------

        URI url = URI.create("http://localhost:8080/tasks/task");
        request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertEquals("{}", HttpTaskServer.getHttpTaskManager().getMapTask().toString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

}