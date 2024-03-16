import { useShowTimeline } from "@/api/hooks/useShowTimeline/useShowTimeline";
import { Post, RoastLevel, GrindSize } from "@/api/interfaces";
import { TimelineCard } from "@/features/TimelineCard";

export const TimelinePage = () => {
  //   const showTimeline = useShowTimeline({
  //     order: "asc",
  //   });

  //   const timeline = showTimeline.data.pages.flatMap((v) => v.posts);

  const post: Post = {
    post_id: 1,
    location: "スターバックス",
    way_to_brew: "ラテ",
    roast_level: RoastLevel.light,
    temperature: 100,
    grams_of_coffee: 100,
    grind_size: GrindSize.finest,
    impression: "感想",
    origin: "ブラジル",
  };

  return (
    <div>
      <TimelineCard key={post.post_id} post={post} />
      {/* {timeline.map((post) => {
        return <TimelineCard key={post.post_id} post={post} />;
      })} */}
    </div>
  );
};
