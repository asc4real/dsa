# Chain of Responsibility — Notification Delivery Example

A good Chain of Responsibility use case for notifications is **fallback delivery**:

```text
Notification Request
        ↓
PushNotificationHandler
        ↓ if unavailable/failed
SmsNotificationHandler
        ↓ if unavailable/failed
EmailNotificationHandler
        ↓
No delivery channel available
```

Each handler decides:

```text
Can I deliver this notification?
    ↓
YES → deliver and stop
NO  → forward to next handler
```

## Java Implementation

Here’s a clean Java implementation.

```java
import java.util.Set;

public class NotificationDemo {

    public static void main(String[] args) {

        // Create concrete handlers
        NotificationHandler pushHandler =
                new PushNotificationHandler();

        NotificationHandler smsHandler =
                new SmsNotificationHandler();

        NotificationHandler emailHandler =
                new EmailNotificationHandler();

        // Build the chain
        pushHandler
                .setNext(smsHandler)
                .setNext(emailHandler);

        // User supports SMS and EMAIL, but not PUSH
        NotificationRequest request =
                new NotificationRequest(
                        "USR-1001",
                        "Your payment of ₹2,500 was successful.",
                        Set.of(
                                NotificationChannel.SMS,
                                NotificationChannel.EMAIL
                        )
                );

        boolean delivered = pushHandler.handle(request);

        if (!delivered) {
            System.out.println(
                    "Notification could not be delivered through any channel."
            );
        }
    }
}
```

### 1. Notification channel

```java
enum NotificationChannel {

    PUSH,
    SMS,
    EMAIL
}
```

### 2. Notification request

This is the object flowing through the chain.

```java
import java.util.Set;

class NotificationRequest {

    private final String userId;
    private final String message;
    private final Set<NotificationChannel> enabledChannels;

    public NotificationRequest(
            String userId,
            String message,
            Set<NotificationChannel> enabledChannels
    ) {
        this.userId = userId;
        this.message = message;
        this.enabledChannels = enabledChannels;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isChannelEnabled(NotificationChannel channel) {
        return enabledChannels.contains(channel);
    }
}
```

### 3. Handler abstraction

This is the core Chain of Responsibility class.

```java
abstract class NotificationHandler {

    private NotificationHandler nextHandler;

    public NotificationHandler setNext(
            NotificationHandler nextHandler
    ) {
        this.nextHandler = nextHandler;

        // Returning next handler allows:
        // push.setNext(sms).setNext(email);
        return nextHandler;
    }

    public boolean handle(NotificationRequest request) {

        if (canHandle(request)) {

            send(request);

            return true;
        }

        if (nextHandler != null) {
            return nextHandler.handle(request);
        }

        return false;
    }

    protected abstract boolean canHandle(
            NotificationRequest request
    );

    protected abstract void send(
            NotificationRequest request
    );
}
```

The important part is:

```java
if (canHandle(request)) {
    send(request);
    return true;
}

if (nextHandler != null) {
    return nextHandler.handle(request);
}
```

Every handler gets a chance to process the request.

### 4. Push notification handler

```java
class PushNotificationHandler
        extends NotificationHandler {

    @Override
    protected boolean canHandle(
            NotificationRequest request
    ) {

        System.out.println(
                "Checking whether PUSH notification is available..."
        );

        return request.isChannelEnabled(
                NotificationChannel.PUSH
        );
    }

    @Override
    protected void send(
            NotificationRequest request
    ) {

        System.out.println(
                "PUSH notification sent to user "
                        + request.getUserId()
                        + ": "
                        + request.getMessage()
        );
    }
}
```

### 5. SMS handler

```java
class SmsNotificationHandler
        extends NotificationHandler {

    @Override
    protected boolean canHandle(
            NotificationRequest request
    ) {

        System.out.println(
                "Checking whether SMS notification is available..."
        );

        return request.isChannelEnabled(
                NotificationChannel.SMS
        );
    }

    @Override
    protected void send(
            NotificationRequest request
    ) {

        System.out.println(
                "SMS sent to user "
                        + request.getUserId()
                        + ": "
                        + request.getMessage()
        );
    }
}
```

### 6. Email handler

```java
class EmailNotificationHandler
        extends NotificationHandler {

    @Override
    protected boolean canHandle(
            NotificationRequest request
    ) {

        System.out.println(
                "Checking whether EMAIL notification is available..."
        );

        return request.isChannelEnabled(
                NotificationChannel.EMAIL
        );
    }

    @Override
    protected void send(
            NotificationRequest request
    ) {

        System.out.println(
                "EMAIL sent to user "
                        + request.getUserId()
                        + ": "
                        + request.getMessage()
        );
    }
}
```

## Execution Flow

With our request:

```java
Set.of(
    NotificationChannel.SMS,
    NotificationChannel.EMAIL
)
```

the chain is:

```text
Push
 │
 │ PUSH enabled?
 │ No
 ▼
SMS
 │
 │ SMS enabled?
 │ Yes
 ▼
Send SMS

STOP
```

Output:

```text
Checking whether PUSH notification is available...
Checking whether SMS notification is available...
SMS sent to user USR-1001: Your payment of ₹2,500 was successful.
```

The email handler is never invoked because SMS successfully handled the request.

## How the Participants Map to the GoF Pattern

| GoF Participant | Our implementation |
|---|---|
| Handler | `NotificationHandler` |
| Concrete Handler 1 | `PushNotificationHandler` |
| Concrete Handler 2 | `SmsNotificationHandler` |
| Concrete Handler 3 | `EmailNotificationHandler` |
| Request | `NotificationRequest` |
| Client | `NotificationDemo` |

Architecturally:

```text
                    NotificationHandler
                           ▲
             ┌─────────────┼─────────────┐
             │             │             │
             │             │             │
          PushHandler    SmsHandler    EmailHandler
```

```text
Client
  │
  ▼
PushNotificationHandler
  │
  │ cannot handle
  ▼
SmsNotificationHandler
  │
  │ handles
  ▼
SMS delivered
```

The key advantage is that the caller does not write:

```java
if (pushAvailable) {
    sendPush();
} else if (smsAvailable) {
    sendSms();
} else if (emailAvailable) {
    sendEmail();
}
```

Instead it simply does:

```java
pushHandler.handle(request);
```

The chain itself determines who handles the notification.

## A More Realistic Production Version

In a real Spring Boot application, I would keep the same pattern but inject actual services:

```java
@Component
public class SmsNotificationHandler
        extends NotificationHandler {

    private final SmsService smsService;

    public SmsNotificationHandler(
            SmsService smsService
    ) {
        this.smsService = smsService;
    }

    @Override
    protected boolean canHandle(
            NotificationRequest request
    ) {
        return request.isChannelEnabled(
                NotificationChannel.SMS
        );
    }

    @Override
    protected void send(
            NotificationRequest request
    ) {
        smsService.send(
                request.getUserId(),
                request.getMessage()
        );
    }
}
```

Then your chain represents an actual delivery fallback:

```text
Push provider
     ↓ failure/unavailable
SMS provider
     ↓ failure/unavailable
Email provider
```

That version becomes especially useful when you add things like provider outages, user preferences, invalid phone numbers, missing email addresses, retryability, rate limits, and channel priority.

---

# Python Version

Absolutely. Here’s the same Chain of Responsibility notification-delivery example in Python, using three concrete handlers:

```text
Push → SMS → Email
```

The first handler capable of delivering the notification handles it, and the chain stops.

> **Source note:** The Python implementation appears as a single flattened paragraph in the original DOCX. It is preserved below without inventing code that is not present in the source.

```python
from abc import ABC, abstractmethodclass EmailNotificationHandler(NotificationHandler):    def can_handle(        self,        request: NotificationRequest    ) -> bool:        print("Checking whether EMAIL notification is available...")        return request.is_channel_enabled(            NotificationChannel.EMAIL        )    def send(        self,        request: NotificationRequest    ) -> None:        print(            f"EMAIL sent to user "            f"{request.user_id}: {request.message}"        )def main():    # Create concrete handlers    push_handler = PushNotificationHandler()    sms_handler = SmsNotificationHandler()    email_handler = EmailNotificationHandler()    # Build the Chain of Responsibility    push_handler \        .set_next(sms_handler) \        .set_next(email_handler)    # User has SMS and Email enabled,    # but PUSH is unavailable    request = NotificationRequest(        user_id="USR-1001",        message="Your payment of ₹2,500 was successful.",        enabled_channels={            NotificationChannel.SMS,            NotificationChannel.EMAIL        }    )    delivered = push_handler.handle(request)    if not delivered:        print(            "Notification could not be delivered "            "through any available channel."        )if __name__ == "__main__":    main()
```

The output will be:

```text
Checking whether PUSH notification is available...
Checking whether SMS notification is available...
SMS sent to user USR-1001: Your payment of ₹2,500 was successful.
```

The execution is:

```text
NotificationRequest
        │
        ▼
PushNotificationHandler
        │
        │ can_handle() = False
        ▼
SmsNotificationHandler
        │
        │ can_handle() = True
        ▼
      send()
        │
        ▼
       STOP

EmailNotificationHandler
        ✗
   never invoked
```

## GoF Participants in Python

| Chain of Responsibility participant | Python class |
|---|---|
| Handler | `NotificationHandler` |
| Concrete Handler | `PushNotificationHandler` |
| Concrete Handler | `SmsNotificationHandler` |
| Concrete Handler | `EmailNotificationHandler` |
| Request | `NotificationRequest` |
| Client | `main()` |

The most important part is inside `handle()`:

```python
def handle(self, request):    if self.can_handle(request):        self.send(request)        return True    if self._next_handler:        return self._next_handler.handle(request)    return False
```

Conceptually, every handler says:

```text
Can I handle this request?

YES
 ↓
Handle it
 ↓
STOP

NO
 ↓
Do I have a next handler?

YES
 ↓
Forward request

NO
 ↓
Return failure
```

And notice that `main()` does not contain logic like:

```python
if push_enabled:    send_push()elif sms_enabled:    send_sms()elif email_enabled:    send_email()
```

Instead, the client only knows:

```python
push_handler.handle(request)
```

That separation is the essence of the pattern: the sender starts the request, while the chain determines which concrete handler ultimately handles it.
