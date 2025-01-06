package pl.wmsdev.data.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.dto.PersonalDataUser;
import pl.wmsdev.usos4j.client.UsosUserAPI;
import pl.wmsdev.usos4j.model.user.UsosUser;
import pl.wmsdev.utils.converter.PersonalDataUserConverter;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PersonalDataUserServiceImpl implements PersonalDataUserService {

    private final UsosUserAPI userApi;
    private final PersonalDataUserConverter personalDataUserConverter;

    @Async
    @Override
    public CompletableFuture<PersonalDataUser> getPersonalDataUser() {
        UsosUser user = userApi.users().user();

        return CompletableFuture.completedFuture(personalDataUserConverter.toPersonalDataUser(user));
    }
}
