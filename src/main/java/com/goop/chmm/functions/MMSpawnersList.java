package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.spawning.spawners.MythicSpawner;
import org.bukkit.Location;

import java.util.Iterator;

@api
public class MMSpawnersList extends AbstractFunction {
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

		AbstractLocation loc = BukkitAdapter.adapt(new BukkitMCLocation(env.getEnv(CommandHelperEnvironment.class).GetPlayer().getLocation()).asLocation());
		CArray spawners = new CArray(t);
		Iterator iterator = MythicMobs.inst().getSpawnerManager().getSpawners().iterator();

		while(iterator.hasNext()) {
			MythicSpawner ms = (MythicSpawner) iterator.next();
			CArray spawner = new CArray(t);

			if(args.length == 3) {

				MCWorld world = Static.getWorld(args[1], t);
				Location location = new BukkitMCLocation(ObjectGenerator.GetGenerator().location(args[0], world, t)).asLocation();
				loc = BukkitAdapter.adapt(location);
				double radius = Double.parseDouble(args[2].val());

				if(ms.getLocation().getWorld() != null && ms.getLocation().getWorld().equals(loc.getWorld()) && ms.distanceTo(loc) <= radius) {

					spawner.set("name", ms.getName());
					spawner.set("group", ms.getGroup());

					CArray msloc = new CArray(t);
					msloc.set("x", new CInt(ms.getBlockX(), t), t);
					msloc.set("y", new CInt(ms.getBlockY(), t), t);
					msloc.set("z", new CInt(ms.getBlockZ(), t), t);
					msloc.set("world", new CString(ms.getWorldName(), t), t);
					spawner.set("location", msloc, t);

					spawners.push(spawner, t);

				}

			} else {

				MCWorld world = env.getEnv(CommandHelperEnvironment.class).GetPlayer().getWorld();

				spawner.set("name", ms.getName());
				spawner.set("group", ms.getGroup());

				CArray msloc = new CArray(t);
				msloc.set("x", new CInt(ms.getBlockX(), t), t);
				msloc.set("y", new CInt(ms.getBlockY(), t), t);
				msloc.set("z", new CInt(ms.getBlockZ(), t), t);
				msloc.set("world", new CString(ms.getWorldName(), t), t);
				spawner.set("location", msloc, t);

				spawners.push(spawner, t);

			}

		}

		return spawners;

	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_spawners_list";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[]{ 0, 3 };
	}

	@Override
	public String docs() {
		return "array ([location, world, radius]) get spawners list";
	}
}
