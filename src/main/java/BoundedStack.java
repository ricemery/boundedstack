import com.google.common.base.Optional;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class BoundedStack<T> {
   private final Deque<T> list = new ConcurrentLinkedDeque<>();
   private final int maxEntries;

   public BoundedStack(final int maxEntries) {
      checkArgument(maxEntries > 0, "maxEntries must be greater than zero");
      this.maxEntries = maxEntries;
   }

   public void push(T item) {
      checkNotNull(item, "item must not be null");

      list.push(item);
      if (list.size() > maxEntries) {
         list.removeLast();
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
