import { useShowTimeline } from "@/api/hooks/useShowTimeline/useShowTimeline";
import { Button } from "@/components/ui/button";
import { TimelineCard } from "@/features/TimelineCard";

export const TimelinePage = () => {
  const showTimeline = useShowTimeline({
    order: "asc",
  });

  const timeline = showTimeline.data.pages.flatMap((v) => v.posts);

  return (
    <div className="grid gap-5 p-3 pb-32">
      {timeline.map((post, index) => {
        return (
          <>
            <TimelineCard key={post.post_id} post={post} />
            {timeline.length !== index + 1 && <hr />}
          </>
        );
      })}

      {showTimeline.hasNextPage && (
        <Button onClick={() => showTimeline.fetchNextPage()}>もっとみる</Button>
      )}
    </div>
  );
};