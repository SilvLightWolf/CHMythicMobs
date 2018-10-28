package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCWorld;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractWorld;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;

import java.util.Iterator;

@api
public class MMMobsStats extends AbstractFunction {
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

		int amount = 0;
		int alive = 0;
		int dead = 0;
		int valid = 0;
		int invalid = 0;
		int n = 0;

		MCPlayer p = env.getEnv(CommandHelperEnvironment.class).GetPlayer();
		AbstractWorld world = BukkitAdapter.adapt(new BukkitMCWorld(p.getWorld()).__World());

		Iterator iterator = MythicMobs.inst().getMobManager().getActiveMobs().iterator();

		while(iterator.hasNext()) {
			ActiveMob am = (ActiveMob) iterator.next();
			if(am.getLocation().getWorld().getName().equals(world.getName())) {
				++amount;
				if (am.getEntity() == null) {
					n++;
				} else {
					if (am.getEntity().isDead()) {
						++dead;
					} else {
						++alive;
					}

					if (am.getEntity().isValid()) {
						++valid;
					} else {
						++invalid;
					}
				}
			}
		}

		CArray returnarray = new CArray(t);
		returnarray.set("total", new CInt(amount, t), t);
		returnarray.set("alive", new CInt(alive, t), t);
		returnarray.set("dead", new CInt(dead, t), t);
		returnarray.set("valid", new CInt(valid, t), t);
		returnarray.set("invalid", new CInt(invalid, t), t);
		returnarray.set("unknown", new CInt(n, t), t);

		return returnarray;
	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_mobs_stats";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[] { 0 };
	}

	@Override
	public String docs() {
		return "array () get mythicmobs stats";
	}
}
