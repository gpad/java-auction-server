import com.gpad.auctionserver.AuctionServerApplication;
import com.gpad.auctionserver.AuctionServerConfiguration;
import com.gpad.auctionserver.api.Auction;
import com.gpad.auctionserver.db.AuctionsRepository;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.jdbi.v3.core.Jdbi;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gpad on 17/11/18.
 */
public class AuctionServerTest {

    private static final String TMP_FILE = createTempFile();
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

    private static String createTempFile() {
        try {
            return File.createTempFile("test-example", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @ClassRule
    public static final DropwizardAppRule<AuctionServerConfiguration> RULE = new DropwizardAppRule<>(
            AuctionServerApplication.class, CONFIG_PATH,
            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));

    @BeforeClass
    public static void migrateDb() throws Exception {
        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    }

    @Test
    public void listOfAuctionsShouldAlwaysContainExampleAutions() throws Exception {
        final List<Auction> auctions = getClient().target("http://localhost:" + RULE.getLocalPort() + "/auctions")
                .request()
                .get(new GenericType<List<Auction>>() {
                });

        assertThat(auctions).contains(new Auction(1, "example_1"), new Auction(2, "example_2"), new Auction(3, "example_3"));
    }

    @Test
    public void getASingleAuctionByIdReturnAnAuctionObject() throws Exception {
        AuctionsRepository auctionsRepository = getAuctionsRepository();
        Auction expectedAuction = new Auction(666, "the number of the beast");
        auctionsRepository.insert(expectedAuction);

        final Auction saying = getClient().target("http://localhost:" + RULE.getLocalPort() + "/auctions/666")
                .request()
                .get(Auction.class);

        assertThat(saying).isEqualTo(expectedAuction);
    }

    private Client getClient() {
        return RULE.client();
    }

    private AuctionsRepository getAuctionsRepository() {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(RULE.getEnvironment(), RULE.getConfiguration().getDataSourceFactory(), "h2");
        return jdbi.onDemand(AuctionsRepository.class);
    }

    @Test
    public void getVersionShouldBeDefinedInConfiguration() throws Exception {
        final String version = getClient().target("http://localhost:" + RULE.getLocalPort() + "/version")
                .request()
                .get(String.class);

        assertThat(version).isEqualTo(RULE.getConfiguration().getAuctionServerVersion());
    }


    @Test
    public void testLogFileWritten() throws IOException {
        // The log file is using a size and time based policy, which used to silently
        // fail (and not write to a log file). This test ensures not only that the
        // log file exists, but also contains the log line that jetty prints on startup
        final Path log = Paths.get("./logs/application.log");
        assertThat(log).exists();
        final String actual = new String(Files.readAllBytes(log), UTF_8);
        assertThat(actual).contains("0.0.0.0:" + RULE.getLocalPort());
    }
}
