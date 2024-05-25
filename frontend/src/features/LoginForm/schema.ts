import { z } from "zod";

export const loginSchema = z.object({
  email: z.string().email("正しいメールアドレス形式で入力してください"),
  password: z
    .string({
      required_error: "パスワードを入力してください",
    })
    .min(1, "パスワードを入力してください"),
});

export type LoginSchema = z.infer<typeof loginSchema>;
