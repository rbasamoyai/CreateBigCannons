package rbasamoyai.createbigcannons.munitions;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.CBCEntityTypes;
import rbasamoyai.createbigcannons.CreateBigCannons;

public class Shrapnel extends AbstractHurtingProjectile {

	public static final DamageSource SHRAPNEL = new DamageSource(CreateBigCannons.MOD_ID + ".shrapnel");
	
	private int age;
	
	public Shrapnel(EntityType<? extends Shrapnel> type, Level level) {
		super(type, level);
	}
	
	@Override
	public void tick() {
		super.tick();
		++this.age;
		if (!this.level.isClientSide && this.age > 200) {
			this.discard();
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Age", this.age);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.age = tag.getInt("Age");
	}
	
	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		BlockPos pos = result.getBlockPos();
		this.level.playSound(null, pos, this.level.getBlockState(pos).getSoundType().getBreakSound(), SoundSource.NEUTRAL, 1.0f, 2.0f);
		this.discard();
	}
	
	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (result.getEntity().getType() == CBCEntityTypes.SHRAPNEL.get()) {
			return;
		}
		result.getEntity().hurt(SHRAPNEL, 10.0f);
		result.getEntity().invulnerableTime = 0;
		this.discard();
	}
	
	public static void build(EntityType.Builder<? extends Shrapnel> builder) {
		builder.setTrackingRange(3)
				.setUpdateInterval(20)
				.setShouldReceiveVelocityUpdates(true)
				.fireImmune()
				.sized(0.25f, 0.25f);
	}
	
	@Override protected float getEyeHeight(Pose pose, EntityDimensions dimensions) { return 0.125f; }
	
	@Override protected float getInertia() { return 0.99f; }
	
	public static void spawnShrapnelBurst(Level level, Vec3 position, Vec3 initialVelocity, int count, double spread) {
		Vec3 forward = initialVelocity.normalize();
		Vec3 right = forward.cross(new Vec3(Direction.UP.step()));
		Vec3 up = forward.cross(right);
		double length = initialVelocity.length();
		Random random = level.getRandom();
		
		int failCount = 0;
		
		for (int i = 0; i < count; ++i) {
			double velScale = length * (1.4d + 0.2d * random.nextDouble());
			Vec3 vel = forward.scale(velScale)
					.add(right.scale((random.nextDouble() - random.nextDouble()) * velScale * spread))
					.add(up.scale((random.nextDouble() - random.nextDouble()) * velScale * spread));
			
			Shrapnel shrapnel = new Shrapnel(CBCEntityTypes.SHRAPNEL.get(), level);
			double rx = position.x + (random.nextDouble() - random.nextDouble()) * 0.0625d;
			double ry = position.y + (random.nextDouble() - random.nextDouble()) * 0.0625d;
			double rz = position.z + (random.nextDouble() - random.nextDouble()) * 0.0625d;
			shrapnel.setPos(rx, ry, rz);
			shrapnel.setDeltaMovement(vel);
			
			if (!level.addFreshEntity(shrapnel)) {
				++failCount;
			}
		}
		
		if (failCount > 0) {
			CreateBigCannons.LOGGER.info("Shrapnel burst failed to spawn {} out of {} shrapnel bullets", failCount, count);
		}
	}

}
