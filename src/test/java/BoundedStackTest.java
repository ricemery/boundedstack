import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class BoundedStackTest {
   @Test
   public void testEmpty() {
      final BoundedStack<Integer> stack = new BoundedStack<>(10);
      Assert.assertThat("Should be empty", stack.empty(), is(true));
      stack.push(1);
      Assert.assertThat("Should not be empty", stack.empty(), is(false));
      stack.pop();
      Assert.assertThat("Should be empty again", stack.empty(), is(true));
   }

   @Test
   public void testPeek() {
      final BoundedStack<Integer> stack = new BoundedStack<>(10);
      Assert.assertThat("Should return Optional.absent", stack.peek(), equalTo(Optional.<Integer>absent()));
      stack.push(1);
      stack.push(2);
      Assert.assertThat("Should return top of stack", stack.peek().get(), equalTo(2));
      Assert.assertThat("Top of stack should not be removed", stack.peek().get(), equalTo(2));
      stack.push(3);
      Assert.assertThat("Should return new top of stack", stack.peek().get(), equalTo(3));
   }

   @Test
   public void testPushAndPop() {
      final BoundedStack<Integer> stack = new BoundedStack<>(10);
      stack.push(1);
      Assert.assertThat("Should equal last pushed", stack.pop().get(), is(1));
      Assert.assertThat("Should return Optional.absent", stack.pop(), equalTo(Optional.<Integer>absent()));
      Assert.assertThat("Should return Optional.absent again", stack.pop(), equalTo(Optional.<Integer>absent()));

      stack.push(1);
      stack.push(2);
      stack.push(3);
      Assert.assertThat("Should equal last pushed", stack.pop().get(), is(3));
   }

   @Test
   public void testBoundary() {
      final BoundedStack<Integer> stack = new BoundedStack<>(5);

      stack.push(1);
      stack.push(2);
      stack.push(3);
      stack.push(4);
      stack.push(5);
      stack.push(6);

      Assert.assertThat("Should equal last pushed", stack.pop().get(), is(6));
      Assert.assertThat(stack.pop().get(), is(5));
      Assert.assertThat(stack.pop().get(), is(4));
      Assert.assertThat(stack.pop().get(), is(3));
      Assert.assertThat(stack.pop().get(), is(2));
      Assert.assertThat("Should be empty", stack.empty(), is(true));
   }
}
