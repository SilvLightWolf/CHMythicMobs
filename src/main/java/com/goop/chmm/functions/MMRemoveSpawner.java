package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.spawning.spawners.MythicSpawner;

@api
public class MMRemoveSpawner extends AbstractFunction {

	@Override
	public Class<? extends CREThrowable>[] thrown() {
		return new Class[]{
				CRENotFoundException.class
		};
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

		MythicSpawner ms = MythicMobs.inst().getSpawnerManager().getSpawnerByName(args[0].val());
		if(ms == null) {
			throw new CRENotFoundException("Unknown spawner : " + args[0].val(), t);
		} else {
			MythicMobs.inst().getSpawnerManager().removeSpawner(ms);
			return CBoolean.TRUE;
		}

	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_remove_spawner";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[]{ 1 };
	}

	@Override
	public String docs() {
		return "boolean (spawnerName) remove spawnerName.";
	}
}
