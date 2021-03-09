package pl.projects.testing.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.projects.testing.order.Order;
import pl.projects.testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartHandler cartHandler;
    @Captor
    private ArgumentCaptor<Cart> argumentCaptor;

    @Test
    void processCartShouldSendToPrepare() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        Cart resultCart = cartService.processCart(cart);

        verify(cartHandler).sendToPrepare(cart);
        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeast(1)).sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void processCartShouldNotSendToPrepare() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(cart)).thenReturn(false);

        Cart resultCart = cartService.processCart(cart);

        verify(cartHandler, never()).sendToPrepare(cart);
        assertThat(cart.getOrders(), hasSize(1));
        assertThat(cart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldNotSendToPrepareWitchArgumentMatchers() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(any(Cart.class))).thenReturn(false);

        Cart resultCart = cartService.processCart(cart);

        verify(cartHandler, never()).sendToPrepare(any(Cart.class));
        assertThat(cart.getOrders(), hasSize(1));
        assertThat(cart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(any(Cart.class))).thenReturn(true, false, true, true);

        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
    }

    @Test
    void processCartShouldSendToPrepareWitchLambda() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0 ))).thenReturn(true);

        Cart resultCart = cartService.processCart(cart);

        verify(cartHandler).sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void canHandleCartShouldThrowException() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(cart)).thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
    }

    @Test
    void processCartShouldSendToPrepareWitchArgumentCaptor() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        Cart resultCart = cartService.processCart(cart);

        verify(cartHandler).sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));
    }

    @Test
    void shouldDoNothingWhenProcessCart() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        doNothing().when(cartHandler).sendToPrepare(cart);

        Cart resultCart = cartService.processCart(cart);

        verify(cartHandler).sendToPrepare(cart);
    }

    @Test
    void shouldAnswerWhenProcessCart() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        doAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);

        when(cartHandler.canHandleCart(cart)).then(i -> {
            Cart argumentCart = i.getArgument(0);
            argumentCart.clearCart();
            return true;
        });

        Cart resultCart = cartService.processCart(cart);

        assertThat(resultCart.getOrders().size(), equalTo(0));
    }

    @Test
    void deliveryShouldBeFree() {
        Cart cart = new Cart();
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());

        when(cartHandler.isDeliveryFree(cart)).thenCallRealMethod();

        boolean isDeliveryFree = cartHandler.isDeliveryFree(cart);

        assertTrue(isDeliveryFree);
    }
}
