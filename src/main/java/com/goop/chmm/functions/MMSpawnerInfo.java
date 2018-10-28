package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.spawning.spawners.MythicSpawner;

import java.util.UUID;

@api
public class MMSpawnerInfo extends AbstractFunction {

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

			CArray info = new CArray(t);

			info.set("name", ms.getName());

			CArray location = new CArray(t);
			location.set("x", new CInt(ms.getBlockX(), t), t);
			location.set("y", new CInt(ms.getBlockY(), t), t);
			location.set("z", new CInt(ms.getBlockZ(), t), t);
			location.set("x", ms.getLocation().getWorld().getName());
			info.set("location", location, t);

			CArray spawnradius = new CArray(t);
			spawnradius.set("x", new CInt(ms.getSpawnRadius(), t), t);
			spawnradius.set("y", new CInt(ms.getSpawnRadiusY(), t), t);
			info.set("spawnradius", spawnradius, t);

			CArray conds = new CArray(t);
			for(String str : ms.getConditionList()) {
				conds.push(new CString(str, t), t);
			}
			info.set("conditions", conds, t);

			info.set("mobname", ms.getInternalName());
			info.set("group", ms.getGroup());
			info.set("maxmobs", new CInt(ms.getMaxMobs(), t), t);
			info.set("moblevel", new CInt(ms.getMobLevel().get(), t), t);
			info.set("perspawn", new CInt(ms.getMobsPerSpawn(), t), t);
			info.set("activationrange", new CInt(ms.getActivationRange(), t), t);
			info.set("leashrange", new CInt(ms.getLeashRange(), t), t);
			info.set("healonleash", CBoolean.get(ms.isHealOnLeash()), t);
			info.set("cooldown", new CInt(ms.getCooldownSeconds(), t), t);
			info.set("warmup", new CInt(ms.getWarmupSeconds(), t), t);
			info.set("breakable", CBoolean.get(ms.isBreakable()), t);
			info.set("spawned", new CInt(ms.getNumberOfMobs(), t), t);
			info.set("cached", new CInt(ms.getNumberOfCachedMobs(), t), t);
			info.set("is_cooldown", CBoolean.get(ms.isOnCooldown()), t);
			info.set("remainingcooldown", new CInt(ms.getRemainingCooldownSeconds(), t), t);
			info.set("is_warmup", CBoolean.get(ms.isOnWarmup()), t);
			info.set("remainingwarmup", new CInt(ms.getRemainingWarmupSeconds(), t), t);
			info.set("internalcooldown", new CInt(ms.getInternalCooldown(), t), t);

			CArray associatedmobs = new CArray(t);
			for(UUID uuid : ms.getAssociatedMobs()) {
				associatedmobs.push(new CString(uuid.toString(), t), t);
			}
			info.set("associated", associatedmobs, t);

			return info;
		}

	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_spawner_info";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[]{ 1 };
	}

	@Override
	public String docs() {
		return "array (spawnerName) get Spawner info";
	}
}
