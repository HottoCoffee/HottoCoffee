import { useForm } from "react-hook-form";
import { SignUpSchema, signUpSchema } from "./schema";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Form,
  FormControl,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import { useSignUp } from "@/api/hooks/useSignUp";
import { useTransitionNavigate } from "@/hook/useTransitionNavigate";

export const SignUpForm = () => {
  const signUp = useSignUp();
  const { transitionNavigate } = useTransitionNavigate();

  const form = useForm<SignUpSchema>({
    resolver: zodResolver(signUpSchema),
    defaultValues: {},
  });

  return (
    <Form {...form}>
      <form
        className="flex gap-6 flex-col w-full f-full"
        onSubmit={form.handleSubmit((state) => {
          return signUp.mutateAsync(
            {
              ...state,
              display_name: state.display_name as string,
            },
            {
              onSuccess: () => {
                transitionNavigate("/login", "slide-to-right");
              },
            },
          );
        })}
      >
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>メールアドレス ※必須</FormLabel>
              <FormControl>
                <Input placeholder="example@example.com" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormLabel>パスワード ※必須</FormLabel>
              <FormControl>
                <Input type="password" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="account_id"
          render={({ field }) => (
            <FormItem>
              <FormLabel>アカウントID ※必須</FormLabel>
              <FormControl>
                <Input placeholder="hotto_coffee_taro" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="display_name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>表示名</FormLabel>
              <FormControl>
                <Input placeholder="コーヒー太郎" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="introduction"
          render={({ field }) => (
            <FormItem>
              <FormLabel>説明欄</FormLabel>
              <FormControl>
                <Textarea placeholder="私は人間です。" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="icon_url"
          render={({ field }) => (
            <FormItem>
              <FormLabel>アイコンのURL</FormLabel>
              <FormControl>
                <Input placeholder="https://picsum.photos/200/300" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <Button type="submit">登録する</Button>
      </form>
    </Form>
  );
};
