package ch.zli.m223.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;
    Entry entry = new Entry();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<Entry> index() {
        return entryService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public Entry create(Entry entry) {
        final LocalDateTime checkIn = entry.getCheckIn();
        final LocalDateTime checkOut = entry.getCheckOut();
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("CheckIn must be before CheckOut");
        } else if (checkIn.isEqual(checkOut)) {
            throw new IllegalArgumentException("CheckIn must be before CheckOut");
        }
       return entryService.createEntry(entry);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes an entry.", description = "Deletes an entry by its id.")
    public Response deleteEntry(@PathParam("id") Long id) {
        entryService.deleteEntry(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an entry.", description = "Updates an entry by its id.")
    public Entry updateEntry(@PathParam("id") Long id, Entry entry) {
        return entryService.updateEntry(id, entry);
    }
}
