import com.google.common.base.Optional;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class BoundedStack<T> {
   private final Deque<T> list = new ConcurrentLinkedDeque<>();
   private final int maxEntries;
   private final ReentrantLock lock = new ReentrantLock();


   public BoundedStack(final int maxEntries) {
      checkArgument(maxEntries > 0, "maxEntries must be greater than zero");
      this.maxEntries = maxEntries;
   }

   public void push(final T item) {
      checkNotNull(item, "item must not be null");

      lock.lock();
      try {
         list.push(item);
         if (list.size() > maxEntries) {
            list.removeLast();
         }
      } finally {
         lock.unlock();
      }
   }

   public Optional<T> pop() {
      return Optional.fromNullable(list.poll());
   }

   public Optional<T> peek() {
      return Optional.fromNullable(list.peekFirst());
   }

   public boolean empty() {
      return list.isEmpty();
   }
}
