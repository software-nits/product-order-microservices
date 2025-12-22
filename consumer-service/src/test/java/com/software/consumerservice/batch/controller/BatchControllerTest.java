package com.software.consumerservice.batch.controller;

import com.software.consumerservice.batch.service.DeleteRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BatchControllerTest {

    @Test
    void whenJobIsRunning_returnsAlreadyRunningMessage_andDoesNotStartJob() throws Exception {
        DeleteRecordService svc = mock(DeleteRecordService.class);
        when(svc.isJobRunning()).thenReturn(true);

        BatchController controller = new BatchController(svc);

        ResponseEntity<String> resp = controller.deleteRecord();

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Job is already running, please try again later.", resp.getBody());
        verify(svc, never()).startJob();
    }

    @Test
    void whenJobIsNotRunning_startsJobAndReturnsNotRunningMessage() throws Exception {
        DeleteRecordService svc = mock(DeleteRecordService.class);
        when(svc.isJobRunning()).thenReturn(false);

        BatchController controller = new BatchController(svc);

        ResponseEntity<String> resp = controller.deleteRecord();

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Job is not running, please start new job.", resp.getBody());
        verify(svc, times(1)).startJob();
    }

    @Test
    void whenStartJobThrowsJobInstanceAlreadyExistsException_exceptionPropagates() throws Exception {
        DeleteRecordService svc = mock(DeleteRecordService.class);
        when(svc.isJobRunning()).thenReturn(false);
        doThrow(new JobInstanceAlreadyExistsException("exists")).when(svc).startJob();

        BatchController controller = new BatchController(svc);

        assertThrows(JobInstanceAlreadyExistsException.class, controller::deleteRecord);
        verify(svc, times(1)).startJob();
    }

    @Test
    void whenStartJobThrowsNoSuchJobException_exceptionPropagates() throws Exception {
        DeleteRecordService svc = mock(DeleteRecordService.class);
        when(svc.isJobRunning()).thenReturn(false);
        doThrow(new NoSuchJobException("no job")).when(svc).startJob();

        BatchController controller = new BatchController(svc);

        assertThrows(NoSuchJobException.class, controller::deleteRecord);
        verify(svc, times(1)).startJob();
    }

    @Test
    void whenStartJobThrowsJobParametersInvalidException_exceptionPropagates() throws Exception {
        DeleteRecordService svc = mock(DeleteRecordService.class);
        when(svc.isJobRunning()).thenReturn(false);
        doThrow(new JobParametersInvalidException("invalid")).when(svc).startJob();

        BatchController controller = new BatchController(svc);

        assertThrows(JobParametersInvalidException.class, controller::deleteRecord);
        verify(svc, times(1)).startJob();
    }
}

