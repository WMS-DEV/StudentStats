package pl.wmsdev.data.service.personal;

import pl.wmsdev.data.dto.PersonalDataUser;

import java.util.concurrent.CompletableFuture;

public interface PersonalDataUserService {

    CompletableFuture<PersonalDataUser> getPersonalDataUser();
}
