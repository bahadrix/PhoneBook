package me.bahadir.bil425.phonebook.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;


/**
 * Created by bahadir on 16/02/15.
 */
@Api(
        name="contactApi",
        version = "v1",
        namespace = @ApiNamespace(ownerDomain = "backend.phonebook.bil425.bahadir.me", ownerName = "backend.phonebook.bil425.bahadir.me", packagePath = "")
)
public class ContactEndpoint {

    public Response hello(@Named("name") String name) {
        return new Response("Hello " + name);
    }

}
