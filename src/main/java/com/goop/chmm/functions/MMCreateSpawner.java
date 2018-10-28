package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.spawning.spawners.MythicSpawner;
import org.bukkit.Location;

@api
public class MMCreateSpawner extends AbstractFunction {

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

			String mobname = args[1].val();
			String sname = args[2].val();
			Location loc = new BukkitMCLocation(ObjectGenerator.GetGenerator().location(args[0], null, t)).asLocation();

			MythicSpawner ms = MythicMobs.inst().getSpawnerManager().createSpawner(sname, loc, mobname);

			return CBoolean.TRUE;

		}catch(Exception ex){ return CBoolean.FALSE; }
	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_create_spawner";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[]{ 3 };
	}

	@Override
	public String docs() {
		return "boolean (locationArray, mobName, spawnerName) create Spawner";
	}
}
