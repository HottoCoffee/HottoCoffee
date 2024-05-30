import { API_BASE_URL } from "@/api/config";
import { Post } from "@/api/interfaces";
import { useApi } from "@/api/utils";
import { InfiniteData, useSuspenseInfiniteQuery } from "@tanstack/react-query";
import queryString from "query-string";

export const QUERY_KEY_SHOW_TIMELINE = "QUERY_KEY_SHOW_TIMELINE";

type Payload = {
  order: "asc" | "desc";
};
type Response = {
  posts: Post[];
  has_next: boolean;
};

type PageParam = {
  lower_post_id?: number;
  upper_post_id?: number;
};

export const useShowTimeline = (payload: Payload) => {
  const { api } = useApi();

  return useSuspenseInfiniteQuery<
    Response,
    Error,
    InfiniteData<Response>,
    [typeof QUERY_KEY_SHOW_TIMELINE],
    PageParam
  >({
    queryKey: [QUERY_KEY_SHOW_TIMELINE],
    queryFn: async ({ pageParam }) => {
      const params = queryString.stringify(pageParam);

      return api<Payload, Response>(
        {
          path: `/timeline?${params}`,
        },
        "GET",
      );
    },
    initialPageParam: {},
    getNextPageParam: (last): PageParam | undefined => {
      if (!last.has_next) {
        return undefined;
      }

      if (payload.order === "asc") {
        return {
          lower_post_id: last.posts.at(-1)?.post_id,
        };
      }

      return {
        upper_post_id: last.posts.at(-1)?.post_id,
      };
    },
  });
};
