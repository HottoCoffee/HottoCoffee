import { Post } from "@/api/interfaces";
import { FaLocationDot } from "react-icons/fa6";
import { FaEarthAsia } from "react-icons/fa6";
import { FaFireAlt } from "react-icons/fa";
import { BsCup } from "react-icons/bs";
import { FaRegCommentDots } from "react-icons/fa";

type Props = {
  post: Post;
};

export const TimelineCard = (props: Props) => {
  const { post } = props;

  return (
    <div>
      <div className="grid grid-cols-[0fr_1fr] gap-2 items-center">
        <FaLocationDot />
        <p>{post.location}</p>

        <FaEarthAsia />
        <p>{post.origin}</p>

        <FaFireAlt />
        <p>{post.roast_level}</p>

        <BsCup />
        <p>{post.way_to_brew}</p>

        <FaRegCommentDots />
        <p>{post.impression}</p>
      </div>
    </div>
  );
};
