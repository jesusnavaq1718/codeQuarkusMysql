package org.acme;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/personas")
@Produces("application/json")
@Consumes("application/json")
public class GreetingResource {

    @Inject
    DataSource dataSource;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @GET
    @Path("/list")
    public List<JsonNode> getPersonas() {
        List<JsonNode> personas = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM persona")) {

            while (resultSet.next()) {
            	 JsonNode personaNode = objectMapper.createObjectNode()
                         .put("id", resultSet.getLong("id"))
                         .put("nombre", resultSet.getString("nombre"))
                         .put("edad", resultSet.getInt("edad"));
            	 personas.add(personaNode);
            }

        } catch (SQLException e) {
            // Manejo de excepciones
            e.printStackTrace();
        }

        return personas;
    }
    
    @POST
    @Path("/create")
    public Response createPersona(JsonNode  persona) {
        try (Connection connection = dataSource.getConnection()) {
            // Insertar la nueva persona en la base de datos
            String insertQuery = "INSERT INTO persona (nombre, edad) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, persona.get("nombre").asText());
                preparedStatement.setInt(2,persona.get("edad").intValue());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            // Manejo de excepciones
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al crear la persona.")
                    .build();
        }

        return Response.status(Response.Status.CREATED)
                .entity("Persona creada exitosamente.")
                .build();
    }
}
