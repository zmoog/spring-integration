/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.xml.router;

import java.io.IOException;

import javax.xml.transform.Source;

import org.springframework.core.io.Resource;
import org.springframework.integration.core.MessagingException;
import org.springframework.xml.validation.XmlValidationException;
import org.springframework.xml.validation.XmlValidatorFactory;
import org.xml.sax.SAXParseException;

public class SchemaValidator implements XmlValidator {

	private final org.springframework.xml.validation.XmlValidator xmlValidator;

	public SchemaValidator(Resource schemaResource, String schemaLanguage)
			throws IOException {
		super();
		this.xmlValidator = XmlValidatorFactory.createValidator(schemaResource,
				schemaLanguage);
	}

	public boolean isValid(Source source) {
		try {
			SAXParseException[] exceptions = xmlValidator.validate(source);
			return  exceptions.length < 1;
		} catch (IOException ioE) {
			throw new MessagingException(
					"Exception applying schema validation", ioE);
		} catch (XmlValidationException validationException){
			return false;
		}
	}

}