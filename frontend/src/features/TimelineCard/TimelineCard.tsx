import { Post } from "@/api/interfaces";
import * as Accordion from "@radix-ui/react-accordion";
import { ChevronDownIcon } from "@radix-ui/react-icons";
import { cn } from "@/lib/utils";

type Props = {
  post: Post;
};

export const TimelineCard = (props: Props) => {
  const { post } = props;

  return (
    <div className="mx-auto bg-card text-card-foreground p-4 w-full rounded-lg border">
      <div className="flex items-center space-x-4">
        {/* TODO: Set profile image */}
        {/* <img
          src="https://via.placeholder.com/50"
          alt="Profile Picture"
          className="w-12 h-12 rounded-full"
        /> */}
        <div>
          <h2 className="text-lg font-semibold">
            {post.user_info?.display_name}
            <span className="text-muted-foreground">
              {" "}
              @{post.user_info?.account_id}
            </span>
          </h2>
        </div>
      </div>
      <div className="mt-4">
        {post.location && (
          <div className="flex items-center space-x-2">
            <svg
              className="w-5 h-5 text-muted-foreground"
              fill="currentColor"
              viewBox="0 0 24 24"
            >
              <path d="M12 2C8.14 2 5 5.14 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.86-3.14-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5S10.62 6.5 12 6.5s2.5 1.12 2.5 2.5S13.38 11.5 12 11.5z" />
            </svg>
            <span>{post.location}</span>
          </div>
        )}

        <div className="flex items-center space-x-2 mt-2">
          <svg
            className="w-5 h-5 text-muted-foreground"
            fill="currentColor"
            viewBox="0 0 24 24"
          >
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z" />
            <path d="M12.5 7H11v6l5.25 3.15L17 14.92l-4.5-2.67z" />
          </svg>
          <span>{post.origin}</span>
        </div>

        {post.grind_size && (
          <div className="flex items-center space-x-2 mt-2">
            <svg
              className="w-5 h-5 text-destructive"
              fill="currentColor"
              viewBox="0 0 24 24"
            >
              <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
            </svg>
            <span>{post.grind_size}</span>
          </div>
        )}
        {post.way_to_brew && (
          <div className="flex items-center space-x-2 mt-2">
            <svg
              className="w-5 h-5 text-card-foreground"
              fill="currentColor"
              viewBox="0 0 24 24"
            >
              <path d="M18 8h-1V6c0-2.21-1.79-4-4-4S9 3.79 9 6v2H8C6.34 8 5 9.34 5 11v7c0 1.66 1.34 3 3 3h8c1.66 0 3-1.34 3-3v-7c0-1.66-1.34-3-3-3zm-6-2c0-1.1.9-2 2-2s2 .9 2 2v2h-4V6zm6 14H8c-.55 0-1-.45-1-1v-7c0-.55.45-1 1-1h8c.55 0 1 .45 1 1v7c0 .55-.45 1-1 1z" />
            </svg>
            <span>{post.way_to_brew}</span>
          </div>
        )}
        {post.impression && (
          <p className="mt-4 text-card-foreground">{post.impression}</p>
        )}
      </div>
      <Accordion.Root type="single" collapsible className="w-full mt-2">
        <Accordion.Item value="details" className="border-b">
          <Accordion.Header className="flex">
            <Accordion.Trigger
              className={cn(
                "flex flex-1 items-center justify-between py-4 font-medium transition-all hover:underline [&[data-state=open]>svg]:rotate-180"
              )}
            >
              詳細を表示
              <ChevronDownIcon className="h-4 w-4 shrink-0 text-muted-foreground transition-transform duration-200" />
            </Accordion.Trigger>
          </Accordion.Header>
          <Accordion.Content className="overflow-hidden text-sm data-[state=closed]:animate-accordion-up data-[state=open]:animate-accordion-down">
            <div className="pb-4 pt-0">
              <p>hoge</p> {/* Assuming this is placeholder content */}
            </div>
          </Accordion.Content>
        </Accordion.Item>
      </Accordion.Root>
    </div>
  );
};
