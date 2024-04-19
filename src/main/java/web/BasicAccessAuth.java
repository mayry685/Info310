package web;

import dao.CredentialsValidator;
import io.jooby.Extension;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import io.jooby.exception.MissingValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>A Jooby extension that adds a HTTP Basic Access Authentication filter to
 * the filter chain.</p>
 *
 * <p>We intentionally omit sending a realm with the 401 response to stop the
 * browser from trying to handle the authentication itself.</p>
 *
 * <p>Install in Jooby using:</p>
 *
 * <pre><code>install(new BasicAccessAuth(credsValidator, Set.of("/path/to/protect"), Set.of("/path/to/exclude")));</code></pre>
 *
 * @author Mark George
 */
public class BasicAccessAuth implements Extension {

	private final CredentialsValidator validator;
	private final Set<String> protect;
	private final Set<String> exclude;

	private final Logger logger = LoggerFactory.getLogger(BasicAccessAuth.class);

	/**
	 * @param validator The validator to use to check the credentials.
	 * @param protect   A Set that contains paths that should be protected.  Each
	 *                  path is a string that can include regular expressions.
	 *                  <p>Example path:</p> <pre><code>/api/.*</code></pre>
	 * @param exclude   A Set that contains paths that should NOT be protected.
	 *                  Use this to exclude paths that would otherwise be
	 *                  included via wildcard paths in the <code>protect</code>
	 *                  set.  Each path is a string that can include regular
	 *                  expressions.
	 *                  <p>Any paths not included in either set will be ignored
	 *                  and not protected.</p>
	 */
	public BasicAccessAuth(CredentialsValidator validator, Set<String> protect, Set<String> exclude) {
		this.validator = validator;
		this.protect = protect;
		this.exclude = exclude;
	}

	@Override
	public void install(Jooby application) throws Exception {

		application.use(next -> ctx -> {

			// get requested path
			String path = ctx.getRequestPath();

			// check if path is in exclude set
			for (String ex : exclude) {
				if (path.matches(ex)) {
					logger.debug("EXCLUDE - {}", path);

					// if so, no auth required so continue to the next route
					return next.apply(ctx);
				}
			}

			// check if path is in the protect set
			for (String pro : protect) {
				if (path.matches(pro)) {
					logger.debug("PROTECT - {}", path);

					String authToken;
					try {
						authToken = ctx.header("Authorization").value();
					} catch (MissingValueException ex) {
						// Authorization header missing - send 401/Unauthorized response
						return ctx.setResponseHeader("WWW-Authenticate", "None").send(StatusCode.UNAUTHORIZED);
					}

					// strip off the "Basic " part
					String stripped = authToken.replace("Basic ", "");

					Base64.Decoder decoder = Base64.getDecoder();
					String authDetails = new String(decoder.decode(stripped));

					// split the decoded string into username and password
					Matcher matcher = Pattern.compile("(?<username>.+?):(?<password>.*)").matcher(authDetails);

					if (!matcher.matches()) {
						// token is not in the expected format so is likely invalid - send 401/Unauthorized response
						return ctx.setResponseHeader("WWW-Authenticate", "None").send(StatusCode.UNAUTHORIZED);
					}

					String username = matcher.group("username");
					String password = matcher.group("password");

					// check the credentials
					if (validator.credentialCheck(username, password)) {

						// add username to context
						ctx.setUser(username);

						// continue on to next route
						return next.apply(ctx);

					} else {
						// bad credentials - send 401/Unauthorized response
						return ctx.setResponseHeader("WWW-Authenticate", "None").send(StatusCode.UNAUTHORIZED);
					}

				}
			}

			// path is not in protect or exclude set, so carry on to next route
			logger.debug("IGNORE - {}", path);
			return next.apply(ctx);
		});
	}

}