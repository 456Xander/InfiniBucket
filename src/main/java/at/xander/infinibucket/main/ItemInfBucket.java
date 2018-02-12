package at.xander.infinibucket.main;

import at.xander.infinibucket.item.FluidHandlerInfiniBucket;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemInfBucket extends ItemBucket {

	static FluidHandlerInfiniBucket ItemFluidHandler = null;
	private int capacity;

	public ItemInfBucket() {
		this(0);
	}

	public ItemInfBucket(int capacity) {
		super(Blocks.FLOWING_WATER);
		this.capacity = capacity;
		this.setMaxDamage(capacity);
		this.setHasSubtypes(false);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, itemstack.getItemDamage() != 0);

		if (raytraceresult == null) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		} else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}

		BlockPos blockpos = raytraceresult.getBlockPos();
		IBlockState block = worldIn.getBlockState(blockpos);
		if(block.getMaterial() == Material.WATER) {
			worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(),11); // flag 11 = send changes to client and immediately re render
			playerIn.addStat(StatList.getObjectUseStats(this));
            playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
            itemstack.setItemDamage(0);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		else if(capacity != 0 && itemstack.getItemDamage() == capacity) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack); 
		}
		else if (block.getBlock() instanceof BlockCauldron) { // Block is Cauldron
			System.out.println("Filling Cauldron");
			BlockCauldron cauldron = (BlockCauldron) block.getBlock();
			int level = ((Integer) block.getValue(BlockCauldron.LEVEL)).intValue();
			if (level < 3 && !worldIn.isRemote) {
				playerIn.addStat(StatList.CAULDRON_FILLED);
				cauldron.setWaterLevel(worldIn, blockpos, block, 3);
				
				itemstack.damageItem(1, playerIn);

				worldIn.playSound((EntityPlayer) null, blockpos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS,
						1.0F, 1.0F);
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);

		} else {
			boolean flag1 = block.getBlock().isReplaceable(worldIn, blockpos);
			BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos
					: blockpos.offset(raytraceresult.sideHit);

			if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemstack)) {
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
			} else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1)) {
				if (playerIn instanceof EntityPlayerMP) {
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) playerIn, blockpos1, itemstack);
				}

				itemstack.damageItem(1, playerIn);

				playerIn.addStat(StatList.getObjectUseStats(this));
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
			} else {
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
			}
		}

	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (this.getClass() == ItemInfBucket.class) {
			ItemFluidHandler = new FluidHandlerInfiniBucket(stack, capacity == 0, FluidRegistry.WATER);
			return ItemFluidHandler;
		}
		return super.initCapabilities(stack, nbt);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return stack.getItemDamage() != 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return capacity != 0 ? ((double) stack.getItemDamage() / capacity) : 0;
	}

}
