package kr.oror.arabot.MessageUI;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kr.oror.arabot.MainApplication;
import kr.oror.arabot.R;
import kr.oror.arabot.ShowAllActivity;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private final int LONG_MESSAGE_LENGTH = 500;
    private final List<UserMessage> mMessageList;

    public MessageListAdapter(Context context, List<UserMessage> messageList) {
        mMessageList = messageList;
    }

    public void clear() {
        int size = mMessageList.size();
        mMessageList.clear();
        notifyItemRangeRemoved(0, size);
    }

   /* public void addItem(boolean isBot,String msg,String sender, int position) {
        if(item.message.length()>500){
            item.message=item.message.substring(0,500)
        }
        mMessageList.add(position, item);
        notifyItemInserted(position);
    }*/

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        UserMessage message = mMessageList.get(position);

        if (message.getIsBot()) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            // If some other user sent the message

            return VIEW_TYPE_MESSAGE_SENT;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        UserMessage message = mMessageList.get(position);
        final String msgTxt = message.getText();
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Context ctx = MainApplication.getContextForJava();
                Toast.makeText(ctx, ctx.getResources().getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(msgTxt, msgTxt);
                clipboard.setPrimaryClip(clip);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msgTxt.length() > LONG_MESSAGE_LENGTH) {
                    Intent intent = new Intent(MainApplication.getContextForJava(), ShowAllActivity.class);
                    intent.putExtra("showAllData", msgTxt);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainApplication.getContextForJava().startActivity(intent);

                }
            }
        });
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        final TextView messageText;
        final TextView senderText;

        SentMessageHolder(View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.message_sender);
            messageText = itemView.findViewById(R.id.message_body);
        }

        void bind(UserMessage message) {
            String msg = message.getText();
            if (msg.length() > LONG_MESSAGE_LENGTH) {
                msg = msg.substring(0, LONG_MESSAGE_LENGTH) + R.string.messege_more;
            }
            messageText.setText(msg);
            senderText.setText(message.getName());
            // Format the stored timestamp into a readable String using method.

        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        final TextView messageText;


        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message_body);


        }

        void bind(UserMessage message) {
            String msg = message.getText();
            if (msg != null)
                if (msg.length() > LONG_MESSAGE_LENGTH) {
                    msg = msg.substring(0, LONG_MESSAGE_LENGTH) + R.string.messege_more;

                }
            messageText.setText(msg);

            // Format the stored timestamp into a readable String using method.


            // Insert the profile image from the URL into the ImageView.

        }
    }
}