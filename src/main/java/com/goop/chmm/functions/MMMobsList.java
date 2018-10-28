package com.goop.chmm.functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMobStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@api
public class MMMobsList extends AbstractFunction {
	@Override
	public Class<? extends CREThrowable>[] thrown() {
		return new Class[0];
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

		MobManager manager = MythicMobs.inst().getMobManager();
		List<MythicMob> types = new ArrayList(manager.getMobTypes());
		List<MythicMob> stacks = new ArrayList(manager.getMobStacks());
		CArray array = new CArray(t);

		CArray typesarray = new CArray(t);
		Iterator itypes = types.iterator();
		while(itypes.hasNext()) {
			MythicMob mob = (MythicMob) itypes.next();
			typesarray.push(new CString(mob.getInternalName(), t), t);
		}

		CArray stacksarray = new CArray(t);
		Iterator istacks = stacks.iterator();
		while(istacks.hasNext()) {
			MythicMobStack stack = (MythicMobStack) istacks.next();
			stacksarray.push(new CString(stack.getName(), t), t);
		}

		array.set("default", typesarray, t);
		array.set("mobs", stacksarray, t);

		return array;
	}

	@Override
	public Version since() {
		return new SimpleVersion(1, 0, 0);
	}

	@Override
	public String getName() {
		return "mm_mobs_list";
	}

	@Override
	public Integer[] numArgs() {
		return new Integer[] { 0 };
	}

	@Override
	public String docs() {
		return "boolean () get MythicMobs list";
	}
}
