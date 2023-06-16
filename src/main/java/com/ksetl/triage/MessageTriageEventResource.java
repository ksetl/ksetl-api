package com.ksetl.triage;

import com.ksetl.ServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.Objects;

@Path("/message-triage-events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "message-triage-event", description = "MessageTriageEvent Operations")
@AllArgsConstructor
@Slf4j
public class MessageTriageEventResource {

    private final MessageTriageEventService messageTriageEventService;

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All MessageTriageEvents",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = MessageTriageEvent.class)
            )
    )
    public Response get() {
        return Response.ok(messageTriageEventService.findAll()).build();
    }

    @GET
    @Path("/{messageTriageEventId}")
    @APIResponse(
            responseCode = "200",
            description = "Get MessageTriageEvent by messageTriageEventId",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = MessageTriageEvent.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "MessageTriageEvent does not exist for messageTriageEventId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getById(@Parameter(name = "messageTriageEventId", required = true) @PathParam("messageTriageEventId") Long messageTriageEventId) {
        return messageTriageEventService.findById(messageTriageEventId)
                .map(messageTriageEvent -> Response.ok(messageTriageEvent).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @APIResponse(
            responseCode = "201",
            description = "MessageTriageEvent Created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = MessageTriageEvent.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid MessageTriageEvent",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "MessageTriageEvent already exists for messageTriageEventId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response post(@NotNull @Valid MessageTriageEvent messageTriageEvent, @Context UriInfo uriInfo) {
        messageTriageEventService.save(messageTriageEvent);
        URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(messageTriageEvent.getMessageTriageEventId())).build();
        return Response.created(uri).entity(messageTriageEvent).build();
    }

    @PUT
    @Path("/{messageTriageEventId}")
    @APIResponse(
            responseCode = "204",
            description = "MessageTriageEvent updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = MessageTriageEvent.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid MessageTriageEvent",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "MessageTriageEvent object does not have messageTriageEventId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Path variable messageTriageEventId does not match MessageTriageEvent.messageTriageEventId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "No MessageTriageEvent found for messageTriageEventId provided",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response put(@Parameter(name = "messageTriageEventId", required = true) @PathParam("messageTriageEventId") Long messageTriageEventId, @NotNull @Valid MessageTriageEvent messageTriageEvent) {
        if (!Objects.equals(messageTriageEventId, messageTriageEvent.getMessageTriageEventId())) {
            throw new ServiceException("Path variable messageTriageEventId does not match MessageTriageEvent.messageTriageEventId");
        }
        messageTriageEventService.update(messageTriageEvent);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}