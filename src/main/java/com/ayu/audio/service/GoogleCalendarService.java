package com.ayu.audio.service;

import com.ayu.audio.dto.AudioFileCalendarEntryDto;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static com.ayu.audio.constants.AudioConstants.RECORDING_TIME;

@Service
@Slf4j
public class GoogleCalendarService {
    @Value("${application.name}")
    private String APPLICATION_NAME;

    @Value("${google.api.tokens.directory.path}")
    private String TOKENS_DIRECTORY_PATH;

    @Value("${google.api.credential.file}")
    private String CREDENTIALS_FILE;

    @Value("${google.api.receiver.server.port}")
    private String PORT = "8888";

    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream clientSecretsStream = new FileInputStream(ResourceUtils.getFile("classpath:"+"auth.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(clientSecretsStream));
        GoogleAuthorizationCodeFlow flow = getCodeFlowAndAuthorizationRequest(HTTP_TRANSPORT, clientSecrets);
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(Integer.valueOf(PORT)).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private GoogleAuthorizationCodeFlow getCodeFlowAndAuthorizationRequest(NetHttpTransport HTTP_TRANSPORT,
                                                                           GoogleClientSecrets clientSecrets) throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();
    }

    private Calendar getCalendarInstance() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName("AudioFiles")
                .build();
    }

    public AudioFileCalendarEntryDto pushEvents(String fileName, Timestamp fileTimestamp) throws IOException, GeneralSecurityException {
        Calendar calendar = getCalendarInstance();
        Event event = new Event();
        Event.Source source = new Event.Source();
        event.setDescription(fileName);
        event.setSource(source.setTitle(APPLICATION_NAME));
        event.set(RECORDING_TIME, fileTimestamp);
        Calendar.Events.Insert insert = calendar.events().insert(fileName, event);
        Event eventResult = insert.execute();
        if (eventResult.getSummary() != null) {
            return AudioFileCalendarEntryDto.builder().message("Event added in Google calendar.").build();
        } else
            return AudioFileCalendarEntryDto.builder().message("Event not added in Google calendar.").build();
    }
}