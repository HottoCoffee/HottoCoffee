import { useLocalStorage } from "react-use";
import { z } from "zod";

const schema = z.object({
  token: z.string(),
});

export const useToken = () => {
  const states = useLocalStorage<z.infer<typeof schema> | undefined>(
    "token",
    undefined,
    {
      deserializer: (value) => {
        const res = schema.safeParse(JSON.parse(value));

        if (res.success) {
          return res.data;
        }
        return undefined;
      },
      serializer: (v) => {
        return JSON.stringify(v);
      },
      raw: false,
    },
  );

  return states;
};
