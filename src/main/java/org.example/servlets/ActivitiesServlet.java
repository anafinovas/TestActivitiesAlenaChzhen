package org.example.servlets;

import com.google.gson.Gson;
import org.example.DAO.ActivityDB;
import org.example.DTO.ActivityDTO;
import org.example.DTO.PersonActivityDTO;
import org.example.DTO.PersonActivityWithRoleDTO;
import org.example.models.Person;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "activities", value = "/activities")
public class ActivitiesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<PersonActivityDTO> activities = ActivityDB.select();
        PersonActivityWithRoleDTO activitiesAndRole= new PersonActivityWithRoleDTO(getRole(request), activities);
        String json = new Gson().toJson(activitiesAndRole);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ActivityDTO activityDTO = new Gson().fromJson(request.getReader(), ActivityDTO.class);
        ActivityDB.add(activityDTO);
        response.setStatus(200);
    }
    private String getRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");
        return person.getRole();
    }
}
