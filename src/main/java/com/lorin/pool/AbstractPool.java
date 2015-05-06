package com.lorin.pool;

/**
 * 一个理想的release方法应该先尝试检查下这个客户端返回的对象是否还能重复使用。
 * 如果是的话再把它扔回池里，如果不是，就舍弃掉这个对象。我们希望这个Pool接口的所有实现都能遵循这个规则。
 * 在开始具体的实现类前，我们先创建一个抽象类，以便限制后续的实现能遵循这点。我们实现的抽象类就叫做AbstractPool
 * @author taodong
 *
 * @param <T>
 */
public abstract class AbstractPool<T> implements Pool<T> {
	@Override
	public final void release(T t) {
		if (isValid(t)) {
			returnToPool(t);
		} else {
			handleInvalidReturn(t);
		}
	}

	protected abstract void handleInvalidReturn(T t);

	protected abstract void returnToPool(T t);

	protected abstract boolean isValid(T t);
}
