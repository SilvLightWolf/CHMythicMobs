package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.io.MythicConfig;
import io.lumine.xikage.mythicmobs.items.MythicItem;

import java.io.File;

@api
public class MMItemsImport extends AbstractFunction {
	@Override
	public Class<? extends CREThrowable>[] thrown() {
		return new Class[]{};
	}

	@Override
	public boolean isRestricted() {
		return false;
	}

	@Override
	public Boolean runAsync() {
		return null;
	}

	@Override
	public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {

		try {

			MCPlayer p = env.getEnv(CommandHelperEnvironment.class).GetPlayer();
			MCItemStack is = ObjectGenerator.GetGenerator().item(args[0], t);
			String name = args[1].val();

			String path = MythicMobs.inst().getDataFolder() + File.separator + "items" + File.separator + name + ".yml";
			File file = new File(path);
			if(!file.exists()) {
				file.createNewFile();
			}

			MythicConfig config = new MythicConfig(name, file);
			config.load();
			config.set("ItemStack", new BukkitMCItemStack(is).asItemStack());
			config.save();
			MythicItem mi = new MythicItem(file.getName(), name, config);
			MythicMobs.inst().getItemManager().registerItem(name, mi);

			return CBoolean.TRUE;

		}catch(Exception ignored){ return CBoolean.FALSE; }

	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_items_import";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[]{ 2 };
	}

	@Override
	public String docs() {
		return "boolean (itemArray, name) send itemArray to mythicmobs item.";
	}
}
