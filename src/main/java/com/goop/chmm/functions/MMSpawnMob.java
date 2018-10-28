package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;

@api
public class MMSpawnMob extends AbstractFunction {
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

		MCPlayer p = env.getEnv(CommandHelperEnvironment.class).GetPlayer();
		MCLocation loc = p.getLocation();
		int count = 1;

		if(args.length >= 2) {
			loc = ObjectGenerator.GetGenerator().location((CArray) args[1], p.getWorld(), t);
			if(args.length == 3) {
				count = Static.getInt32(args[2], t);
			}
		}

		CArray uuids = new CArray(t);
		ActiveMob am;

		for(int i = 0 ; i < count ; i++) {
			am = MythicMobs.inst().getMobManager().spawnMob(args[0].val(), new BukkitMCLocation(loc).asLocation(), 1);
			uuids.push(new CString(am.getUniqueId().toString(), t), t);
		}

		return CBoolean.TRUE;
	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_spawn_mob";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[] { 1, 2, 3 };
	}

	@Override
	public String docs() {
		return "Array (mobName[, locationArray[, count]]) spawn mythicmobs monster.";
	}
}
