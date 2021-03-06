package org.molgenis.generators.cpp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.molgenis.MolgenisOptions;
import org.molgenis.generators.ForEachEntityGenerator;
import org.molgenis.generators.Generator;
import org.molgenis.generators.GeneratorHelper;
import org.molgenis.model.elements.Entity;
import org.molgenis.model.elements.Model;

import freemarker.template.Template;

public class SourcePerEntityGen extends ForEachEntityGenerator
{
	public static final transient Logger logger = Logger.getLogger(SourcePerEntityGen.class);

	@Override
	public String getDescription()
	{
		return "Generate CPP header files";
	}
	
	@Override
	public String getExtension(){
		return ".cpp";
	}
	
	@Override
	public void generate(Model model, MolgenisOptions options) throws Exception
	{
		Template template = this.createTemplate(this.getClass().getSimpleName() + getExtension() + ".ftl");
		Map<String, Object> templateArgs = createTemplateArguments(options);

		// apply generator to each entity
		for (Entity entity : model.getEntities())
		{
			// calculate package from its own package
			String packageName = entity.getNamespace().toLowerCase() + this.getClass().getPackage().toString().substring(Generator.class.getPackage().toString().length());
			File targetDir = new File((this.getSourcePath(options).endsWith("/")?this.getSourcePath(options):this.getSourcePath(options)+"/") + packageName.replace(".", "/").replace("/cpp", ""));
			try{
				File targetFile = new File(targetDir + "/" + GeneratorHelper.getJavaName(entity.getName()) + getExtension());
				targetDir.mkdirs();
				templateArgs.put("entity", entity);
				templateArgs.put("model", model);
				templateArgs.put("options", options);
				templateArgs.put("template", template.getName());
				templateArgs.put("file", targetDir.getCanonicalPath().replace("\\", "/") + "/" + GeneratorHelper.firstToUpper(entity.getName()) + getType() + getExtension());
				templateArgs.put("package", packageName);
				
				OutputStream targetOut = new FileOutputStream(targetFile);

				template.process(templateArgs, new OutputStreamWriter(targetOut));
				targetOut.close();
				logger.info("generated " + targetFile);
			}
			catch (Exception e)
			{
				logger.error("problem generating for " + entity.getName());
				throw e;
			}
		}
	}
	
	@Override
	public String getSourcePath(MolgenisOptions options)
	{
		return options.output_cpp;
	}

}
