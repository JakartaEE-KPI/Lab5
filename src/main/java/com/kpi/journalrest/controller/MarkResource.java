package com.kpi.journalrest.controller;

import com.kpi.journalrest.common.ErrorResponse;
import com.kpi.journalrest.common.Page;
import com.kpi.journalrest.dto.MarkDto;
import com.kpi.journalrest.dto.MarkRequest;
import com.kpi.journalrest.common.PageRequest;
import com.kpi.journalrest.dto.MarksPageDto;
import com.kpi.journalrest.dto.PaginationDto;
import com.kpi.journalrest.entity.Mark;
import com.kpi.journalrest.mapper.MarkMapper;
import com.kpi.journalrest.service.JournalService;
import com.kpi.journalrest.service.MarkService;
import com.kpi.journalrest.service.StudentService;
import com.kpi.journalrest.service.SubjectService;
import com.kpi.journalrest.validation.MarkValidator;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/marks")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Valid
public class MarkResource {

    @EJB
    MarkService markService;

    @EJB
    StudentService studentService;

    @EJB
    SubjectService subjectService;

    @EJB
    JournalService journalService;

    @EJB
    MarkMapper markMapper;

    @EJB
    MarkValidator markValidator;

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        return Response.ok()
                .entity(markMapper.toDto(markService.getById(id)))
                .build();
    }

    @GET
    @Path("/journals/{journalId}/subjects/{subjectId}/students/{studentId}")
    @Produces(APPLICATION_JSON)
    public Response findStudentSubjectMarks(@PathParam("journalId") Long journalId,
                                            @PathParam("subjectId") Long subjectId,
                                            @PathParam("studentId") Long studentId,
                                            @QueryParam("page") Integer page,
                                            @QueryParam("size") Integer size) {
        Response notExist = this.checkExistence(journalId, subjectId, studentId);
        if (notExist != null) {
            return notExist;
        }
        Page<List<Mark>> pageable = markService.findStudentSubjectMarks(studentId, subjectId, journalId, PageRequest.of(page, size));

        PaginationDto pagination = PaginationDto.builder()
                .currentPage(pageable.getCurrentPage())
                .totalPages(pageable.getTotalPages())
                .totalResults(pageable.getTotalResults())
                .build();

        MarksPageDto marksPage = MarksPageDto.builder()
                .marks(markMapper.toDtoList(pageable.getData()))
                .pagination(pagination)
                .build();

        return Response.ok()
                .entity(marksPage)
                .build();
    }

    @POST
    @Path("/journals/{journalId}/subjects/{subjectId}/students/{studentId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response assignStudent(@PathParam("journalId") Long journalId,
                                  @PathParam("subjectId") Long subjectId,
                                  @PathParam("studentId") Long studentId,
                                  MarkRequest markRequest) {
        Response validationConstraint = markValidator.validateMarkFields(markRequest);
        if (validationConstraint != null) {
            return validationConstraint;
        }
        if (markRequest.getPresence() == null) {
            markRequest.setPresence(true);
        }
        Response notExist = this.checkExistence(journalId, subjectId, studentId);
        if (notExist != null) {
            return notExist;
        }
        return Response.status(CREATED)
                .entity(markMapper.toDto(markService.save(Mark.builder()
                        .point(markRequest.getPoint())
                        .presence(markRequest.getPresence())
                        .student(studentService.findById(studentId))
                        .subject(subjectService.findById(subjectId))
                        .build())))
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response patchMarkUpdate(@PathParam("id") Long id, MarkRequest markRequest) {
        Response validationConstraint = markValidator.validateMarkFields(markRequest);
        if (validationConstraint != null) {
            return validationConstraint;
        }
        Mark updatedMark = markMapper.patchUpdate(markService.getById(id), markRequest);
        markService.save(updatedMark);
        return Response.ok()
                .entity(markMapper.toDto(updatedMark))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response putMarkUpdate(@PathParam("id") Long id, MarkRequest markRequest) {
        Response validationConstraint = markValidator.validateMarkFields(markRequest);
        if (validationConstraint != null) {
            return validationConstraint;
        }
        if (markRequest.getPresence() == null) {
            markRequest.setPresence(true);
        }
        Mark updatedMark = markMapper.putUpdate(markService.getById(id), markRequest);
        markService.save(updatedMark);
        return Response.ok()
                .entity(markMapper.toDto(updatedMark))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMark(@PathParam("id") Long id) {
        markService.deleteById(id);
        return Response.ok()
                .build();
    }

    private Response checkExistence(Long journalId, Long subjectId, Long studentId) {
        if (!journalService.existById(journalId)) {
            return Response.status(NOT_FOUND)
                    .entity(ErrorResponse.notFound("journal by id=" + journalId))
                    .build();
        }
        if (!subjectService.existById(subjectId)) {
            return Response.status(NOT_FOUND)
                    .entity(ErrorResponse.notFound("subject by id=" + subjectId))
                    .build();
        }
        if (!studentService.existById(studentId)) {
            return Response.status(NOT_FOUND)
                    .entity(ErrorResponse.notFound("student by id=" + studentId))
                    .build();
        }
        return null;
    }

}
