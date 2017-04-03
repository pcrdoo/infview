package metaschema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MetaschemaValidator {
	public void validate(String metaschema) throws IOException, MetaschemaValidationException {
		try (InputStream inputStream = new FileInputStream(new File("src/res/metametaschema.json"))) {
			JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
			Schema schema = SchemaLoader.load(rawSchema);
			try {
				schema.validate(new JSONObject(metaschema));
			} catch (ValidationException e) {
				throw new MetaschemaValidationException(e.getMessage());
			}
		}
	}
}
