package org.molgenis.generators.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.StringReader;
import java.net.URL;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.log4j.Logger;
import org.molgenis.Molgenis;
import org.molgenis.MolgenisOptions;
import org.molgenis.framework.ui.MolgenisOriginalStyle;
import org.molgenis.generators.Generator;
import org.molgenis.model.elements.Model;

public class MolgenisResourceCopyGen extends Generator
{
	public static final String RESOURCE_FOLDER = "org/molgenis/framework/ui/res/";
	private final static transient Logger logger = Logger.getLogger(MolgenisResourceCopyGen.class.getSimpleName());

	@Override
	public String getDescription()
	{
		return "Copies all resources into ${output_web}/generated-res.";
	}

	@Override
	public void generate(Model model, MolgenisOptions options) throws Exception
	{
		String jarPath = getClass().getResource("").getFile();

		// System.out.println(jarPath);
		if (jarPath.contains("!"))
		{

			jarPath = jarPath.split("!")[0].split("file:")[1];

			File target = new File(this.getWebserverPath(options) + "/generated-res");

			// check if the target exists otherwise it's created
			if (!target.exists())
			{
				boolean succes = target.mkdirs();
				if (!succes) throw new Exception("can't create /generated-res directory!");
			}

			JarFile jar = new JarFile(jarPath);
			Enumeration entries = jar.entries();
			while (entries.hasMoreElements())
			{
				JarEntry file = (JarEntry) entries.nextElement();
				if (file.getName().contains(RESOURCE_FOLDER))
				{
					if (!file.isDirectory())
					{
						ZipEntry zipEntry = jar.getEntry(file.getName());
						InputStream is = jar.getInputStream(zipEntry);
						String outFilePath = file.getName().replace(RESOURCE_FOLDER, target.getPath() + File.separator);
						logger.info(outFilePath);

						File dst = new File(outFilePath);
						dst.mkdirs();
						if (dst.exists())
						{
							dst.delete();
						}
						dst.createNewFile();
						copyFile(outFilePath, is, dst);
					}
				}
			}

			logger.info("generated " + target);
		}
		else
		{
			// copy the images/scripts/css
			File source = new File(MolgenisOriginalStyle.class.getResource("res").getFile().replace("%20", " "));
			File target = new File(this.getWebserverPath(options).replace("%20", " ") + "/generated-res");

			// deledeleteDirectory(target);
			copyDirectory(source, target);
			logger.info("generated " + target);
		}
	}

	public void copyFile(String srcPath, InputStream in, File dst) throws IOException
	{
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
		logger.debug("copied " + srcPath + " to " + dst);
	}

	public void deleteDirectory(File dir)
	{
		// delete subdirectories
		if (dir.exists()) for (File f : dir.listFiles())
		{
			// skip .svn
			if (f.isDirectory()) deleteDirectory(f);
			else if (f.getAbsolutePath().contains(".svn")) f.delete();
		}
	}

	// from almanac
	public void copyDirectory(File srcDir, File dstDir) throws IOException
	{

		if (!srcDir.getName().contains(".svn"))
		{
			if (srcDir.isDirectory())
			{
				if (!dstDir.exists())
				{
					dstDir.mkdir();
				}

				String[] children = srcDir.list();
				for (int i = 0; i < children.length; i++)
				{
					copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]));
				}
			}
			else
			{
				// This method is implemented in e1071 Copying a File
				copyFile(srcDir, dstDir);
			}
		}
	}

	// from almanac
	public void copyFile(File src, File dst) throws IOException
	{

		if (!src.getAbsolutePath().contains(".svn") && !dst.exists())
		{
			InputStream in = new FileInputStream(src.getAbsolutePath().replace("%20", " "));
			OutputStream out = new FileOutputStream(dst);

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			logger.debug("copied " + src + " to " + dst);
		}
		else
		{
			logger.debug("skipped because exists/svn: " + dst);
		}
	}

}
